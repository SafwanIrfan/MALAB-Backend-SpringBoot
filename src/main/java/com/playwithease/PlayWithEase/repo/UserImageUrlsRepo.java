package com.playwithease.PlayWithEase.repo;

import com.playwithease.PlayWithEase.model.UserImageUrl;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserImageUrlsRepo extends JpaRepository<UserImageUrl,Long> {
}
