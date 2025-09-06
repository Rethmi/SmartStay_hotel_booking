// ReviewController.java
package lk.ijse.gdse72.backend.controller;

import jakarta.validation.Valid;
import lk.ijse.gdse72.backend.dto.ResponseDTO;
import lk.ijse.gdse72.backend.dto.ReviewDTO;
import lk.ijse.gdse72.backend.service.ReviewService;
import lk.ijse.gdse72.backend.service.UserService;
import lk.ijse.gdse72.backend.service.impl.ReviewServiceImpl;
import lk.ijse.gdse72.backend.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/review")
@CrossOrigin
public class ReviewController {
    private final ReviewService reviewService;
    private final ReviewServiceImpl reviewServiceImpl;
    private final UserService userService;

    @Autowired
    JwtUtil jwtUtil;

    public ReviewController(ReviewService reviewService, ReviewServiceImpl reviewServiceImpl, UserService userService) {
        this.reviewService = reviewService;
        this.reviewServiceImpl = reviewServiceImpl;
        this.userService = userService;
    }

    @PostMapping("/save")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> saveReview(@RequestBody @Valid ReviewDTO reviewDTO,@RequestHeader("Authorization") String token) {
        System.out.println("date"+reviewDTO.getReviewDate());
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        reviewServiceImpl.save(reviewDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Review Saved Successfully", null));
    }

    @DeleteMapping(value = "/delete/{id}")
    public ResponseEntity <ResponseDTO> deleteReview(@PathVariable Long id) {
        reviewService.delete(id);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", null));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<ResponseDTO> updateReview(@PathVariable Long id, @RequestBody @Valid ReviewDTO reviewDTO) {
        reviewServiceImpl.update(id,reviewDTO);
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Review Updated Successfully", null));
    }

    @GetMapping("getAll")
    @PreAuthorize("hasAnyAuthority('USER')")
    public ResponseEntity<ResponseDTO> getAllReviews(@RequestHeader("Authorization") String token) {
//        jwtUtil.getUserRoleCodeFromToken(token.substring(7));
        return ResponseEntity.status(HttpStatus.OK)
                .body(new ResponseDTO(HttpStatus.OK.value(), "Success", reviewService.getAll()));
    }
}