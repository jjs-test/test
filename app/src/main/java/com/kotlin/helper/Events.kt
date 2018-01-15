package com.kotlin.helper

import com.kotlin.api.model.ItemModel

class Events {
    class LikeItemSelect(val itemModel: ItemModel)

    class UnlikeItemSelect(val itemModel: ItemModel)
}
