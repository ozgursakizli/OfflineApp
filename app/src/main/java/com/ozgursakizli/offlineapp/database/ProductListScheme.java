package com.ozgursakizli.offlineapp.database;

/**
 * Created by ozgursakizli on 10.09.2017.
 */

public class ProductListScheme {

    public static final class ProductListTable {
        public static final String NAME = "TBLPRODUCTLIST";

        public static final class Columns {
            public static final String productID = "PRODUCTID";
            public static final String productName = "PRODUCTNAME";
            public static final String productPrice = "PRODUCTPRICE";
            public static final String productImage = "PRODUCTIMAGE";

            public static final int productIDKey = 1;
            public static final int productNameKey = 2;
            public static final int productPriceKey = 3;
            public static final int productImageKey = 4;
        }
    }

}
