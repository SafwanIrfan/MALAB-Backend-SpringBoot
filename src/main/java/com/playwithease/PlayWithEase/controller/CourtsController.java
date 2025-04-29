package com.playwithease.PlayWithEase.controller;
import com.cloudinary.Cloudinary;
import com.playwithease.PlayWithEase.model.*;
import com.playwithease.PlayWithEase.service.CourtsService;

import com.playwithease.PlayWithEase.service.SlotsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalTime;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin
public class CourtsController {

    private final Cloudinary cloudinary;

    @Autowired
    public CourtsController(Cloudinary cloudinary){
        this.cloudinary = cloudinary;
    }

    @Autowired
    CourtsService service;

    @Autowired
    SlotsService slotsService;

    @GetMapping("/courts")
    public ResponseEntity<List<Court>> getAllCourts(){
        return new ResponseEntity<>(service.getAllCourts(), HttpStatus.OK);
    }

    @PostMapping("/court/add")
    public ResponseEntity<Court> addCourt(@RequestBody Court court) {
        Court savedCourt = service.addCourt(court);
        return new ResponseEntity<>(savedCourt, HttpStatus.OK);
    }

    @PostMapping("/court/{courtId}/addImage")
    public ResponseEntity<String> addCourtImages(@PathVariable Long courtId,@RequestParam("files") MultipartFile[] files) throws IOException {
        List<String> imageUrl = service.addCourtImages(courtId, files);
        return new ResponseEntity<>("Image added", HttpStatus.OK);
    }

    @DeleteMapping("/court/{courtId}/image/{imageId}")
    public ResponseEntity<String> deleteCourtImage(@PathVariable Long courtId, @PathVariable Long imageId){
        service.deleteCourtImage(courtId, imageId);
        return new ResponseEntity<>("Image Deleted Successfully", HttpStatus.OK);
    }

    @GetMapping("/court/{id}")
    public ResponseEntity<Court> getCourtById (@PathVariable Long id){
        return new ResponseEntity<>(service.getCourtById(id),HttpStatus.OK);
    }

    @GetMapping("/courts/images")
    public ResponseEntity<List<ImageUrls>> getAllImages(){
        List<ImageUrls> images = service.getAllImages();
        return ResponseEntity.ok(images);
    }

    @GetMapping("/court/{courtId}/images")
    public ResponseEntity<List<ImageUrls>> getCourtImages(@PathVariable Long courtId){
        List<ImageUrls> images = service.getCourtImages(courtId);
        return ResponseEntity.ok(images);
    }

    @PutMapping("court/{id}/edit")
    public ResponseEntity<String> editCourt(@PathVariable Long id, @RequestBody Court updatedCourt){
        Court newCourt = service.editCourt(id,updatedCourt);
        if(newCourt != null){
            return new ResponseEntity<>("Updated", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Failed to update", HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping("court/{id}/delete")
    public ResponseEntity<String> deleteCourt(@PathVariable Long id){
            service.deleteCourt(id);
            return new ResponseEntity<>("Deleted", HttpStatus.OK);
    }

    @GetMapping("courts/search")
    public ResponseEntity<List<Court>> searchCourts(@RequestParam String keyword){
        System.out.println("Searching : "+ keyword);
        List<Court> courts = service.searchCourts(keyword);
        return new ResponseEntity<>(courts,HttpStatus.OK);
    }

    @GetMapping("courts/search/words")
    public ResponseEntity<List<String>> searchCourtsByWords(@RequestParam String keyword){
        System.out.println("Searching : "+ keyword);
        List<String> courtsByWords = service.searchCourtsByWords(keyword);
        return new ResponseEntity<>(courtsByWords,HttpStatus.OK);
    }

//    @GetMapping("court/{courtId}/slots")
//    public ResponseEntity<List<BookedSlots>> getBookedSlots(@PathVariable Long courtId){
//        List<BookedSlots> slots = slotsService.getSlots(courtId);
//        return new ResponseEntity<>(slots, HttpStatus.OK);
//    }


    @PostMapping("court/{courtId}/add_timings")
    public ResponseEntity<List<Timings>> addCourtTimings(
            @PathVariable Long courtId,
            @RequestBody List<Timings> timings
    ) {
        List<Timings> newTimings = service.addCourtTimings(courtId, timings);
            return new ResponseEntity<>(newTimings, HttpStatus.OK);

    }

    @GetMapping("court/{courtId}/timings")
    public ResponseEntity<List<Timings>> getCourtTimings(@PathVariable Long courtId){
        List<Timings> timings = service.getCourtTimings(courtId);
        return new ResponseEntity<>(timings, HttpStatus.OK);
    }

    @GetMapping("court/{courtId}/timings/{day}")
    public ResponseEntity<Timings> getCourtTimingsForDay(@PathVariable Long courtId, @PathVariable String day){
        Timings timings = service.getCourtTimingsForDay(courtId,day);
        return new ResponseEntity<>(timings, HttpStatus.OK);
    }

    @PostMapping("court/{courtId}/{day}/book")
    public ResponseEntity<BookedSlots> addSlot(@PathVariable Long courtId, @RequestBody BookedSlots slot){
        BookedSlots bookSlot = slotsService.addSlot(courtId,slot);
        return new ResponseEntity<>(bookSlot, HttpStatus.OK);
    }

    @GetMapping("court/{courtId}/booked_slots")
    public ResponseEntity<List<BookedSlots>> getAllBookedSlots(@PathVariable Long courtId){
        List<BookedSlots> allBookedSlots =  slotsService.getAllBookedSlots(courtId);
        return new ResponseEntity<>(allBookedSlots, HttpStatus.OK);
    }

    @GetMapping("court/{courtId}/{day}/booked_slots")
    public ResponseEntity<List<BookedSlots>> getAllBookedSlotsByDay(@PathVariable Long courtId,@PathVariable String day){
        Court court = service.getCourtById(courtId);
        List<BookedSlots> allBookedSlotsByDay =  slotsService.getAllBookedSlotsByDay(courtId,day);
        return new ResponseEntity<>(allBookedSlotsByDay, HttpStatus.OK);
    }

//    @GetMapping("/slots/{day}/{date}")
//    public ResponseEntity<BookedSlots> getSlotsForDayAndDate (@PathVariable String day, Date date){
//        BookedSlots slotForDayAndDate = slotsService.getSlotsForDayAndDate(day,date);
//        return new ResponseEntity<>(slotForDayAndDate, HttpStatus.OK);
//    }





}
