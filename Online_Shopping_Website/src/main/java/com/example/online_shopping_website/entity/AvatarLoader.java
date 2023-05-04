package com.example.online_shopping_website.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvatarLoader extends BaseEntity implements Serializable {
    String username;
    MultipartFile avatarFile;

}
