package com.jpabook.jpashop.exception

//class NotEnoughStockException(s: String) : Throwable() {
//
//}


class NotEnoughStockException : RuntimeException {
    constructor()
    constructor(message: String?) : super(message)
    constructor(message: String?, cause: Throwable?) : super(message, cause)
    constructor(cause: Throwable?) : super(cause)

}

