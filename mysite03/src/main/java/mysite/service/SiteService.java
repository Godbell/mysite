package mysite.service;

import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import mysite.repository.SiteRepository;
import mysite.vo.SiteVo;

@Service
public class SiteService {
    private final SiteRepository siteRepository;

    public SiteService(SiteRepository siteRepository) {
        this.siteRepository = siteRepository;
    }

    @Cacheable("siteMetadata")
    public SiteVo getSiteInfo() {
        return siteRepository.get();
    }

    @CachePut("siteMetadata")
    public SiteVo updateSiteInfo(SiteVo vo) {
        siteRepository.update(vo);

        // return metadata from db to cache
        // selected from db considering blank input
        return siteRepository.get();
    }
}
