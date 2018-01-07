package com.kotlin.api

interface OnApiResponse<in T> {
    object Ask {
        /* JSON Parsing 실패 했을 때 쓰는 코드 */
        internal val PARSE_FAIL_CODE = 0x2500
        /*
        network 관련 실패일 경우 쓰는 코드
        okhttp 가 call 이 실패하거나, 중간에 cancel 되거나 할 때
         */
        val NETWORK_FAIL_CODE = 0x2501
        /* 뭔가가 잘못되었을 때 쓰는 코드 */
        val SOMETHING_FAIL_CODE = 0x2502

        /* 성공 코드 */
        val SUCCESS = 200

        /**
         *
         * @param code
         * @return
         */
        fun shouldCheckReason(code: Int): Boolean {
            return code != PARSE_FAIL_CODE && code != NETWORK_FAIL_CODE
        }
    }

    fun onSuccess(code: Int, t: T)
    fun onFail(code: Int, e: Exception)
}