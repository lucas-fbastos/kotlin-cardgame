package constants

import androidx.compose.ui.graphics.ImageBitmap
import helper.ImageHelper

internal const val ASSETS_PATH = "assets/cards"

internal val deadlyHamster by lazy { "$ASSETS_PATH/deadly_hamster.png"}

internal val deadlyHamsterRef : ImageBitmap by lazy {
    ImageHelper.loadImageFromResources(deadlyHamster)
}

internal val phoneStealerOtter by lazy { "$ASSETS_PATH/phone_stealer_otter.png" }

internal val phoneStealerOtterRef : ImageBitmap by lazy {
    ImageHelper.loadImageFromResources(phoneStealerOtter)
}

internal val alchemistHedgehog by lazy { "$ASSETS_PATH/alchemist_hedgehog.png" }

internal val alchemistHedgehogRef : ImageBitmap by lazy {
    ImageHelper.loadImageFromResources(alchemistHedgehog)
}