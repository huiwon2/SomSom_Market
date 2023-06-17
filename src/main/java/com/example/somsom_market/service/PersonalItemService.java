package com.example.somsom_market.service;

import com.example.somsom_market.controller.PersonalItem.PersonalItemRequest;
import com.example.somsom_market.dao.PersonalItemDao;
import com.example.somsom_market.domain.ItemStatus;
import com.example.somsom_market.domain.item.PersonalItem;
import com.example.somsom_market.repository.PersonalItemRepository;
import com.example.somsom_market.repository.WishlistRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PersonalItemService {
    // Dao 선언
    @Autowired
    private PersonalItemDao personalItemDao;
    @Autowired
    private PersonalItemRepository personalItemRepository;
    @Autowired
    private WishlistRepository wishlistRepository;

    @Value("${resources.location}")
    private String resourcesLocation;
    @Value("${resources.uri_path:}")
    private String resourcesUriPath;

    // 개인 게시글 리스트
    public List<PersonalItem> personalItemList() {
        List<PersonalItem> personalItemList = personalItemRepository.findAllByOrderByStartDateDesc();
                // personalItemDao.findAll();
        return personalItemList;
    }

    // itemId로 검색
    public PersonalItem searchItem(long itemId) {
        Optional<PersonalItem> personalItem = personalItemRepository.findById(itemId);
        if (personalItem.isPresent()) return personalItem.get();
        return null;
    }

    // 새로운 personalItem 생성
    public PersonalItem registerNewItem(PersonalItemRequest itemRegistReq, String userId) throws Exception {
        PersonalItem item = new PersonalItem();
        item.setTitle(itemRegistReq.getTitle());
        item.setPrice(itemRegistReq.getPrice());
        item.setDescription(itemRegistReq.getDescription());
        if (itemRegistReq.getStatus().equals("거래가능")) {
            item.setStatus(ItemStatus.INSTOCK);
        } else if (itemRegistReq.getStatus().equals("거래중")) {
            item.setStatus(ItemStatus.ING);
        } else {
            item.setStatus(ItemStatus.SOLDOUT);
        }
        item.setSellerId(userId);
        item.setStartDate(new Date());

        // 이미지 등록
        String oriImgName = itemRegistReq.getImgFile().getOriginalFilename(); // 원래 이미지 이름
        String imgName = "";

        // 이미지 이름 겹치지 않게 현재 시간 가져오기
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String current_date = simpleDateFormat.format(new Date());

        imgName = current_date + "_" + oriImgName; // 이미지 이름 : 현재시간_원래이미지이름

        // 이미지 경로
        String path = resourcesLocation + File.separator + "images";
        // 폴더 없을 시 생성
        if (!new File(path).exists()) {
            try {
                new File(path).mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        itemRegistReq.getImgFile().transferTo(new File(path+File.separator+imgName));

        item.setImgName(imgName); // 이미지 이름 저장
        item.setImgPath(resourcesUriPath + "/images/" + imgName);
        // 경로 저장

        return personalItemDao.insertItem(item);
    }

    // 아이템 게시글 수정
    public PersonalItem updateItem(PersonalItemRequest itemRegistReq) {
        return personalItemDao.updateItem(itemRegistReq);
    }

    // 아이템 게시글 삭제
    @Transactional
    public void deleteItem(Long itemId) {
        PersonalItem personalItem = searchItem(itemId);
        // wishlist에 추가되어 있는 경우 삭제
        wishlistRepository.deleteByItemId(personalItem.getId());
        personalItemDao.deleteItem(personalItem);
    }
}
