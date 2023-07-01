package Butlers.Ticat.festival.service;

import Butlers.Ticat.festival.entity.Festival;
import Butlers.Ticat.festival.repository.FestivalRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class FestivalService {

    private final FestivalRepository festivalRepository;

    public Page<Festival> findFestivals(int page, int size){
        return festivalRepository.findAll(PageRequest.of(page-1,size));
    }

}
