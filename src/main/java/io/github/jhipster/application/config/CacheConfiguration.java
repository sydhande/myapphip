package io.github.jhipster.application.config;

import io.github.jhipster.config.JHipsterProperties;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.expiry.Duration;
import org.ehcache.expiry.Expirations;
import org.ehcache.jsr107.Eh107Configuration;

import java.util.concurrent.TimeUnit;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.cache.JCacheManagerCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.*;

@Configuration
@EnableCaching
@AutoConfigureAfter(value = { MetricsConfiguration.class })
@AutoConfigureBefore(value = { WebConfigurer.class, DatabaseConfiguration.class })
public class CacheConfiguration {

    private final javax.cache.configuration.Configuration<Object, Object> jcacheConfiguration;

    public CacheConfiguration(JHipsterProperties jHipsterProperties) {
        JHipsterProperties.Cache.Ehcache ehcache =
            jHipsterProperties.getCache().getEhcache();

        jcacheConfiguration = Eh107Configuration.fromEhcacheCacheConfiguration(
            CacheConfigurationBuilder.newCacheConfigurationBuilder(Object.class, Object.class,
                ResourcePoolsBuilder.heap(ehcache.getMaxEntries()))
                .withExpiry(Expirations.timeToLiveExpiration(Duration.of(ehcache.getTimeToLiveSeconds(), TimeUnit.SECONDS)))
                .build());
    }

    @Bean
    public JCacheManagerCustomizer cacheManagerCustomizer() {
        return cm -> {
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_LOGIN_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.repository.UserRepository.USERS_BY_EMAIL_CACHE, jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Authority.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.User.class.getName() + ".authorities", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.PrimeAC.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.PrimeAC.class.getName() + ".generalacs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.GeneralAC.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.GeneralAC.class.getName() + ".mainacs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.MainAC.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.MainAC.class.getName() + ".billingacs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BillingAC.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BillingAC.class.getName() + ".relatedracs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BillingAC.class.getName() + ".relatecracs", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.AccountCatagory.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.OwnerAccount.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.OwnerAccount.class.getName() + ".owners", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.VendorAccount.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Owner.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.Owner.class.getName() + ".owneraccounts", jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.AllAccountCode.class.getName(), jcacheConfiguration);
            cm.createCache(io.github.jhipster.application.domain.BillingRelate.class.getName(), jcacheConfiguration);
            // jhipster-needle-ehcache-add-entry
        };
    }
}
