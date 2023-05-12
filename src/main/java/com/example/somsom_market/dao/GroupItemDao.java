package com.example.somsom_market.dao;

import com.example.somsom_market.domain.GroupItem;

public interface GroupItemDao extends ItemDao{
    GroupItem getItem(String itemId);

    String insertGroupItem(GroupItem groupItem);

    String updateGroupItem(GroupItem groupItem);

    void deleteGroupItem(String itemId);

}
