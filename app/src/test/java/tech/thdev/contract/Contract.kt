package tech.thdev.contract

import tech.thdev.flickr.BuildConfig

const val TEST_URL = "https://api.flickr.com/services/rest/?method=flickr.interestingness.getList&api_key=${BuildConfig.FLICKR_API_KEY}&format=json&nojsoncallback=1"
const val TEST_FAIL_URL = "https://api.flickr.com/services/a"