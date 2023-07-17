package Butlers.Ticat.stamp.contoller;

import Butlers.Ticat.auth.interceptor.JwtParseInterceptor;
import Butlers.Ticat.stamp.entity.Stamp;
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

    @PostMapping("/{contentId}")
    public ResponseEntity postStampFestivals(@PathVariable("contentId") Long contentId) {

        long jwtId = JwtParseInterceptor.getAuthenticatedMemberId();

        Stamp stamp = stampService.saveStamp(jwtId, contentId);

        boolean isSaved = stamp != null;

        if (isSaved) {
            return new ResponseEntity<>("스탬프 저장 완료", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("스탬프 저장 실패", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
