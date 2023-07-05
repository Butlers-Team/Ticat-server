package Butlers.Ticat.stamp.contoller;

import Butlers.Ticat.festival.dto.FestivalDto;
import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import Butlers.Ticat.member.entity.Member;
import Butlers.Ticat.member.service.MemberService;
import Butlers.Ticat.stamp.dto.StampDto;
import Butlers.Ticat.stamp.entity.Location;
import Butlers.Ticat.stamp.entity.Stamp;
import Butlers.Ticat.stamp.mapper.StampMapper;
import Butlers.Ticat.stamp.service.StampService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/stamp")
public class StampController {

    private static StampService stampService;
    private static StampMapper stampMapper;
    private static MemberService memberService;
    private static FestivalRepository festivalRepository;

    public StampController(StampService stampService, StampMapper stampMapper,
                           MemberService memberService) {
        this.stampService = stampService;
        this.stampMapper = stampMapper;
        this.memberService = memberService;

    }

    @GetMapping("/nearby")
    public List<Festival> getNearbyFestivals(@RequestBody Location clientLocation) {
        return stampService.getNearbyFestivals(clientLocation);
    }

    @PostMapping("/{memberId}/{contentId}")
    public ResponseEntity postStampFestivals(@PathVariable("memberId") Long memberId,
                                             @PathVariable("contentId") Long contentId) {
        stampService.saveStamp(memberId, contentId);

        return new ResponseEntity<>(HttpStatus.OK);
    }

//    @GetMapping("/{memberId}")
//    public ResponseEntity getStamp(@PathVariable("memberId") Long memberId) {
//
//        return new ResponseEntity<>(stampMapper.stampToResponse(memberService.findMember(memberId)), HttpStatus.OK);
//    }

    @GetMapping("/{memberId}")
    public ResponseEntity getStamp(@PathVariable("memberId") Long memberId,
                                   @RequestParam(defaultValue = "1") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   @RequestParam Integer year, @RequestParam Integer month) {

        PageRequest pageRequest = PageRequest.of(page - 1, size);
        YearMonth searchYearMonth = YearMonth.of(year, month);
        Page<Festival> festivals = festivalRepository.findByYearAndMonth(searchYearMonth, pageRequest);


        return new ResponseEntity<>(stampMapper.stampToResponse(memberService.findMember(memberId)), HttpStatus.OK);
    }






}
