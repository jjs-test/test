package com.kotlin.helper;

import com.kotlin.api.model.ItemModel;

public class Events {
    public static class LikeItemSelect {
        private ItemModel itemModel;
        public LikeItemSelect(ItemModel itemModel) {
            this.itemModel = itemModel;
        }

        public ItemModel getItemModel() {
            return itemModel;
        }
    }

    public static class UnlikeItemSelect {
        private ItemModel itemModel;
        public UnlikeItemSelect(ItemModel itemModel) {
            this.itemModel = itemModel;
        }

        public ItemModel getItemModel() {
            return itemModel;
        }
    }
}
