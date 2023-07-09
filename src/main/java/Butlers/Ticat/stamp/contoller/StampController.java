package Butlers.Ticat.stamp.contoller;

import Butlers.Ticat.stamp.service.StampService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/stamp")
public class StampController {

    private static StampService stampService;

    public StampController(StampService stampService) {
        this.stampService = stampService;

    }

    @PostMapping("/{memberId}/{contentId}")
    public ResponseEntity postStampFestivals(@PathVariable("memberId") Long memberId,
                                             @PathVariable("contentId") Long contentId) {
        stampService.saveStamp(memberId, contentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

}
