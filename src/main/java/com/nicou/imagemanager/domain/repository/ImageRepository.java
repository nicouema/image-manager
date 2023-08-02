package com.nicou.imagemanager.domain.repository;

import com.nicou.imagemanager.domain.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, String> {
}
