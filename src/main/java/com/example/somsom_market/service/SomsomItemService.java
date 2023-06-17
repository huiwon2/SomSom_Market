package com.example.somsom_market.service;

import com.example.somsom_market.controller.SomsomItem.SomsomItemRegistRequest;
import com.example.somsom_market.dao.SomsomItemDao;
import com.example.somsom_market.domain.ItemStatus;
import com.example.somsom_market.domain.item.SomsomItem;
import com.example.somsom_market.repository.SomsomItemRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Service
public class SomsomItemService {

    @Autowired
    private static SomsomItemRepository somsomItemRepository;
    @Autowired
    private SomsomItemDao somsomItemDao;

    @Value("${resources.location}")
    private String resourcesLocation;
    @Value("${resources.uri_path:}")
    private String resourcesUriPath;

//   아이템 검색
    public SomsomItem getSomsomItem(Long id){
        Optional<SomsomItem> somsomItem = somsomItemRepository.findById(id);
        if(somsomItem.isPresent()) {
            return somsomItem.get();
        }
        return null;
    }


//  게시글 등록(dao에)
    public SomsomItem registerSomsomItem(SomsomItemRegistRequest regReq) throws IOException {
        SomsomItem somsomItem = new SomsomItem();
        somsomItem.setSellerId("admin");
        somsomItem.setTitle(regReq.getTitle());
        somsomItem.setPrice(regReq.getPrice());
        somsomItem.setDescription(regReq.getDescription());
        somsomItem.setStatus(ItemStatus.INSTOCK);
        somsomItem.setDescription(regReq.getDescription());
//        somsomItem.setImageUrl(Collections.singletonList(regReq.getImageUrl()));

        // 이미지 등록
        String oriImgName = regReq.getImgFile().getOriginalFilename(); // 원래 이미지 이름
        String imgName = "";

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        String current_date = simpleDateFormat.format(new Date());

        imgName = current_date + "_" + oriImgName; // 이미지 이름 : 현재시간_원래이미지이름

        // 프로그램 폴더 위치 + 프로그램 내에서 저장할 폴더까지의 경로
        String path = resourcesLocation + File.separator + "images";
        // 폴더 없을 시 생성
        if (!new File(path).exists()) {
            try {
                new File(path).mkdirs();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        regReq.getImgFile().transferTo(new File(path+File.separator+imgName));

        somsomItem.setImgName(imgName); // 이미지 이름 저장
        somsomItem.setImgPath(resourcesUriPath + "/images/" + imgName);

        return somsomItemDao.insertSomsomItem(somsomItem);
    }
//    게시글 수정
    public static void updateItem(SomsomItem somsomItem, long id){
        SomsomItem update = somsomItemRepository.findItemById(id);
        update.setTitle(somsomItem.getTitle());
        update.setDescription(somsomItem.getDescription());
        update.setPrice(somsomItem.getPrice());
//        update.setImageUrl(somsomItem.getImageUrl());
        somsomItemRepository.save(update);
    }
//    게시글 삭제
    public static void deleteSomsomItem(long id){
        somsomItemRepository.deleteById(id);
    }
//    상품 등록
    public void saveItem(SomsomItem item) {
        somsomItemRepository.save(item); }
//    읽기
    public Optional<SomsomItem> itemView(long id) { return somsomItemRepository.findById(id); }
    public SomsomItem findOne(Long id) { return somsomItemDao.findOne(id); }
//    전체 리스트 읽기
    public List<SomsomItem> allItemView(){
        return somsomItemDao.findAll();
    }
//    상품 삭제
    public void itemDelete(long id){
        somsomItemRepository.deleteById(id);
    }

//    리스트
    public List<SomsomItem> somsomItemList() {
    List<SomsomItem> somsomItemList = somsomItemDao.findAll();
    return somsomItemList;
}

}
