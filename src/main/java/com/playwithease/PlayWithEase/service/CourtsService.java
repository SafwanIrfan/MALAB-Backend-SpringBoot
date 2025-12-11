package com.playwithease.PlayWithEase.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.playwithease.PlayWithEase.model.Court;

import com.playwithease.PlayWithEase.model.CourtImageUrls;
import com.playwithease.PlayWithEase.model.Enums.CourtStatus;
import com.playwithease.PlayWithEase.model.Timings;
import com.playwithease.PlayWithEase.model.Users;
import com.playwithease.PlayWithEase.repo.CourtTimingsRepo;
import com.playwithease.PlayWithEase.repo.CourtsRepo;
import com.playwithease.PlayWithEase.repo.CourtImageUrlsRepo;
import com.playwithease.PlayWithEase.repo.UsersRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class CourtsService {

    @Autowired
    CourtsRepo repo;

    @Autowired
    UsersRepo usersRepo;

    @Autowired
    CourtTimingsRepo courtTimingsRepo;

    @Autowired
    CourtImageUrlsRepo courtImageUrlsRepo;

    @Autowired
    Cloudinary cloudinary;

    public List<Court> getAllCourts(){
        return repo.findAll();
    }

    public Court getCourtById (Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Court not found with ID : " + id));
    }

    public List<Court> getCourtsByStatus(CourtStatus courtStatus){
        return repo.findByCourtStatus(courtStatus)
                .orElseThrow(() -> new RuntimeException("Courts not found with status : "+ courtStatus));
    }

    public List<Court> getCourtByOwnerId (Long ownerId){
        return repo.findByOwnerId(ownerId)
                .orElseThrow(() -> new RuntimeException("Court not found with ownerId : " + ownerId));
    }

    public Court addCourt(String username, Court court){
        Users user = usersRepo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found to fetch his court."));
        if(repo.existsByCourtName(court.getCourtName())) {
            throw new DataIntegrityViolationException("Court name already exist");
        }
        court.setCourtStatus(CourtStatus.NOT_APPROVED);
        court.setOwner(user);
        return repo.save(court);

    }

    public String approveCourt(Long courtId, boolean isApproved) {
        Court court = repo.findById(courtId)
                .orElseThrow(() -> new RuntimeException("Court not found."));

        court.setCourtStatus(CourtStatus.APPROVED);
        repo.save(court);
        return "Court has been approved successfully.";
    }

    public String addCourtImages(Long courtId, MultipartFile[] courtFiles) throws IOException {
        try {
            Court court = getCourtById(courtId);
            if (court == null) {
                throw new RuntimeException("Court not found");
            }

            List<String> uploadedUrls = new ArrayList<>();

            for (MultipartFile file : courtFiles) {
                Map uploadCourtImage = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String courtImageUrl = uploadCourtImage.get("secure_url").toString();
                CourtImageUrls courtUrl = new CourtImageUrls(courtImageUrl);
                courtUrl.setCourt(court);
                court.getCourtImageUrls().add(courtUrl);
                uploadedUrls.add(courtImageUrl);
            }

//            Map uploadOwnerImage = cloudinary.uploader().upload(ownerFile.getBytes(), ObjectUtils.emptyMap());
//            String ownerImageUrl = uploadOwnerImage.get("secure_url").toString();
//            UserImageUrl ownerUrl = new UserImageUrl(ownerImageUrl);
//            ownerUrl.setCourt(court);
//            court.setOwnerImageUrl(ownerUrl);

            repo.save(court);
            //why not saving courtUrls? because CascadeType.All automatically saved and
            //do other operations of its child.
            return "Court Images saved.";

        } catch (IOException e) {
            throw new RuntimeException("Failed to save court images.", e);
        }
    }

    public List<CourtImageUrls> getAllImages() {
        return courtImageUrlsRepo.findAll();
    }

    public List<CourtImageUrls> getCourtImages(Long courtId){
        Court court = getCourtById(courtId);
        if(court != null){
            return courtImageUrlsRepo.findByCourtId(courtId);
        }
        return null;
    }

    public void deleteCourtImage(Long courtId, Long imageId) {
        Court court = getCourtById(courtId);
        if(court != null) {
            CourtImageUrls image = courtImageUrlsRepo.findById(imageId)
                    .orElseThrow(()-> new RuntimeException("Image not found."));

            deleteImageFromCloudinary(image.getUrl());

            court.getCourtImageUrls().removeIf(img -> img.getId().equals(imageId));

            courtImageUrlsRepo.deleteById(imageId);
        }
    }

    public void deleteImageFromCloudinary(String imageUrl){
        try{
            // Extract public ID from URL
                String publicId = extractPublicIdFromUrl(imageUrl);
                cloudinary.uploader().destroy(publicId, ObjectUtils.emptyMap());
        } catch (IOException e) {
            throw new RuntimeException("Error deleting image from Cloudinary", e);
        }
    }

    private String extractPublicIdFromUrl(String imageUrl){
        // Example: https://res.cloudinary.com/your-cloud/image/upload/v1234567890/imageName.jpg
        // Output: imageName

        String[] parts = imageUrl.split("/");
        String fileNameWithExt = parts[parts.length - 1];
        return fileNameWithExt.substring(0, fileNameWithExt.lastIndexOf(".")); // Removes .jpg, .png, etc.
    }

    public Court editCourt(Long id, Court updatedCourt) {
        Court newCourt = getCourtById(id);

        newCourt.setCourtName(updatedCourt.getCourtName());
        newCourt.setDescription(updatedCourt.getDescription());
        newCourt.setCity(updatedCourt.getCity());
        newCourt.setArea(updatedCourt.getArea());
        newCourt.setPricePerHour(updatedCourt.getPricePerHour());

        List<CourtImageUrls> existingImages = newCourt.getCourtImageUrls();
        for (CourtImageUrls img : existingImages) {
            img.setCourt(null);
        }
        existingImages.clear();

        // 🔐 Only update image URLs if the user actually sent new ones
        if (updatedCourt.getCourtImageUrls() != null && !updatedCourt.getCourtImageUrls().isEmpty()) {
            for (CourtImageUrls imageUrl : updatedCourt.getCourtImageUrls()) {
                imageUrl.setCourt(newCourt); // ensure relationship is intact
                existingImages.add(imageUrl);
            }
        }

        return repo.save(newCourt);
    }

//    deleteImageFromCloudinary(court.getOwnerImageUrl().getUrl());

    public void deleteCourt(Long id){
        Court court = getCourtById(id);
        for(CourtImageUrls imageUrl : court.getCourtImageUrls() ){
            deleteImageFromCloudinary(imageUrl.getUrl());
        }
        repo.deleteById(id);
    }

    public List<Court> searchCourts(String keyword) {
        return repo.searchCourts(keyword);
    }

    @Transactional
    public List<Timings> addCourtTimings(Long courtId, List<Timings> timings) {
        Court court = getCourtById(courtId);
        timings.forEach(timing -> timing.setCourt(court));
        System.out.println("Saving timings: " + timings);
        return courtTimingsRepo.saveAll(timings);
    }


    public List<Timings>  getCourtTimings(Long courtId){
        return courtTimingsRepo.findByCourtId(courtId);
    }

    public Timings getCourtTimingsForDay(Long courtId, String day){
        return courtTimingsRepo.findByCourtIdAndDay(courtId, day);
    }


    public List<String> searchCourtsByWords(String keyword) {
        return repo.searchCourtsByWords(keyword);
    }

}
