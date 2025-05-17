package com.playwithease.PlayWithEase.service;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.playwithease.PlayWithEase.exceptions.UsernameAlreadyExistsException;
import com.playwithease.PlayWithEase.model.Court;

import com.playwithease.PlayWithEase.model.ImageUrls;
import com.playwithease.PlayWithEase.model.SlotDays;
import com.playwithease.PlayWithEase.model.Timings;
import com.playwithease.PlayWithEase.repo.CourtTimingsRepo;
import com.playwithease.PlayWithEase.repo.CourtsRepo;
import com.playwithease.PlayWithEase.repo.ImageUrlsRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class CourtsService {

    @Autowired
    CourtsRepo repo;

    @Autowired
    CourtTimingsRepo courtTimingsRepo;

    @Autowired
    ImageUrlsRepo imageUrlsRepo;

    @Autowired
    Cloudinary cloudinary;

    public List<Court> getAllCourts(){
        return repo.findAll();
    }

    public Court getCourtById (Long id){
        return repo.findById(id)
                .orElseThrow(() -> new RuntimeException("Court not found with ID : " + id));
    }

    public Court addCourt(Court court){
        if(repo.existsByName(court.getName())) {
            throw new UsernameAlreadyExistsException("Court name already exist");
        }
        return repo.save(court);

    }

    public List<String> addCourtImages(Long courtId, MultipartFile[] files) throws IOException {
        try {
            Court court = getCourtById(courtId);
            if (court == null) {
                throw new RuntimeException("Court not found");
            }

            List<String> uploadedUrls = new ArrayList<>();

            for (MultipartFile file : files) {
                Map uploadImage = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.emptyMap());
                String imageUrl = uploadImage.get("secure_url").toString();
                ImageUrls url = new ImageUrls(imageUrl);
                url.setCourt(court);
                court.getImageUrls().add(url);
                uploadedUrls.add(imageUrl);
            }

            repo.save(court);
            return uploadedUrls;

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload image", e);
        }
    }

    public List<ImageUrls> getAllImages() {
        return imageUrlsRepo.findAll();
    }

    public List<ImageUrls> getCourtImages(Long courtId){
        Court court = getCourtById(courtId);
        if(court != null){
            return imageUrlsRepo.findByCourtId(courtId);
        }
        return null;
    }

    public void deleteCourtImage(Long courtId, Long imageId) {
        Court court = getCourtById(courtId);
        if(court != null) {
            ImageUrls image = imageUrlsRepo.findById(imageId)
                    .orElseThrow(()-> new RuntimeException("Image not found."));

            deleteImageFromCloudinary(image.getUrl());

            court.getImageUrls().removeIf(img -> img.getId().equals(imageId));

            imageUrlsRepo.deleteById(imageId);
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

        newCourt.setName(updatedCourt.getName());
        newCourt.setDescription(updatedCourt.getDescription());
        newCourt.setLocation(updatedCourt.getLocation());
        newCourt.setPricePerHour(updatedCourt.getPricePerHour());

        List<ImageUrls> existingImages = newCourt.getImageUrls();
        for (ImageUrls img : existingImages) {
            img.setCourt(null);
        }
        existingImages.clear();

        // 🔐 Only update image URLs if the user actually sent new ones
        if (updatedCourt.getImageUrls() != null && !updatedCourt.getImageUrls().isEmpty()) {
            for (ImageUrls imageUrl : updatedCourt.getImageUrls()) {
                imageUrl.setCourt(newCourt); // ensure relationship is intact
                existingImages.add(imageUrl);
            }
        }

        return repo.save(newCourt);
    }


    public void deleteCourt(Long id){
        Court court = getCourtById(id);
        for(ImageUrls imageUrl : court.getImageUrls()  ){
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
