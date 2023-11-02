\c cstore;

DROP TRIGGER IF EXISTS "update_variant" ON "varies_on"; DROP FUNCTION IF EXISTS "update_variant" CASCADE;
DROP TRIGGER IF EXISTS "update_cart" ON "cart_item"; DROP FUNCTION IF EXISTS "update_cart" CASCADE;
DROP TRIGGER IF EXISTS "delete_order_items" ON "order"; DROP FUNCTION IF EXISTS "delete_order_items" CASCADE;

DROP FUNCTION IF EXISTS "unmarketable_properties" CASCADE;
DROP FUNCTION IF EXISTS "search_products_by_name" CASCADE;
DROP FUNCTION IF EXISTS "rank_categories_by_orders" CASCADE;
DROP FUNCTION IF EXISTS "quarters_with_most_interest" CASCADE;
DROP FUNCTION IF EXISTS "properties_from_product" CASCADE;
DROP FUNCTION IF EXISTS "products_with_most_sales" CASCADE;
DROP FUNCTION IF EXISTS "products_from_category" CASCADE;
DROP FUNCTION IF EXISTS "images_from_product" CASCADE;
DROP FUNCTION IF EXISTS "customer_order_report" CASCADE;
DROP FUNCTION IF EXISTS "count_stocks" CASCADE;
DROP FUNCTION IF EXISTS "categories_from_product" CASCADE;
DROP FUNCTION IF EXISTS "buy_now" CASCADE;

DROP PROCEDURE IF EXISTS "update_inventory" CASCADE;
DROP PROCEDURE IF EXISTS "stock" CASCADE;
DROP PROCEDURE IF EXISTS "add_to_cart" CASCADE;

DROP VIEW IF EXISTS "leaf_category" CASCADE;
DROP VIEW IF EXISTS "root_category" CASCADE;

DROP TABLE IF EXISTS "sales_item" CASCADE;
DROP TABLE IF EXISTS "sales_report" CASCADE;
DROP TABLE IF EXISTS "order_item" CASCADE;
DROP TABLE IF EXISTS "order_contact" CASCADE;
DROP TABLE IF EXISTS "order" CASCADE;
DROP TABLE IF EXISTS "cart_item" CASCADE;
DROP TABLE IF EXISTS "cart" CASCADE;
DROP TABLE IF EXISTS "user_address" CASCADE;
DROP TABLE IF EXISTS "user_contact" CASCADE;
DROP TABLE IF EXISTS "registered_user" CASCADE;
DROP TABLE IF EXISTS "user" CASCADE;
DROP TABLE IF EXISTS "inventory" CASCADE;
DROP TABLE IF EXISTS "warehouse_contact" CASCADE;
DROP TABLE IF EXISTS "warehouse" CASCADE;
DROP TABLE IF EXISTS "varies_on" CASCADE;
DROP TABLE IF EXISTS "variant" CASCADE;
DROP TABLE IF EXISTS "property" CASCADE;
DROP TABLE IF EXISTS "belongs_to" CASCADE;
DROP TABLE IF EXISTS "product_image" CASCADE;
DROP TABLE IF EXISTS "product" CASCADE;
DROP TABLE IF EXISTS "image" CASCADE;
DROP TABLE IF EXISTS "sub_category" CASCADE;
DROP TABLE IF EXISTS "category" CASCADE;

-- Category
DROP TABLE IF EXISTS "category";
CREATE TABLE "category" (
     "category_id"          BIGSERIAL,
     "category_name"        VARCHAR (40),
     "category_description" VARCHAR,
     PRIMARY KEY ("category_id")
);

-- Sub Category
DROP TABLE IF EXISTS "sub_category";
CREATE TABLE "sub_category" (
    "category_id"     BIGINT,
    "sub_category_id" BIGINT,
    CHECK ("category_id" != "sub_category_id"),
    PRIMARY KEY ("category_id", "sub_category_id"),
    FOREIGN KEY ("category_id") REFERENCES "category" ("category_id") ON DELETE CASCADE,
    FOREIGN KEY ("sub_category_id") REFERENCES "category" ("category_id") ON DELETE CASCADE
);

-- Image
DROP TABLE IF EXISTS "image";
CREATE TABLE "image" (
     "product_id" BIGINT,
     "url"        VARCHAR (100),
     PRIMARY KEY ("product_id", "url")
);

-- WholeProduct
DROP TABLE IF EXISTS "product";
CREATE TABLE "product" (
    "product_id"   BIGSERIAL,
    "product_name" VARCHAR (100),
    "base_price"   NUMERIC (10, 2) DEFAULT 0,
    "brand"        VARCHAR (40),
    "description"  VARCHAR,
    "main_image"    VARCHAR (100),
    PRIMARY KEY ("product_id")
);

-- Belongs to
DROP TABLE IF EXISTS "belongs_to";
CREATE TABLE "belongs_to" (
    "category_id" BIGINT,
    "product_id"  BIGINT,
    PRIMARY KEY ("category_id", "product_id"),
    FOREIGN KEY ("category_id") REFERENCES "category" ("category_id") ON DELETE CASCADE,
    FOREIGN KEY ("product_id") REFERENCES "product" ("product_id") ON DELETE CASCADE
);

-- ProductSelectionProperty
DROP TABLE IF EXISTS "property";
CREATE TABLE "property" (
    "property_id"     BIGSERIAL,
    "property_name"   VARCHAR (40),
    "value"           VARCHAR (40),
    "url"             VARCHAR (100),
    "price_increment" NUMERIC (10, 2) DEFAULT 0,
    PRIMARY KEY ("property_id")
);

-- Variant
DROP TABLE IF EXISTS "variant";
CREATE TABLE "variant" (
    "variant_id" BIGSERIAL,
    "price"      NUMERIC (10, 2) DEFAULT 0,
    "weight"     NUMERIC (8, 2),
    PRIMARY KEY ("variant_id")
);

-- Varies, based on
DROP TABLE IF EXISTS "varies_on";
CREATE TABLE "varies_on" (
    "varies_on_id" BIGSERIAL,
    "product_id"   BIGINT,
    "property_id"  BIGINT,
    "variant_id"   BIGINT,
    PRIMARY KEY ("varies_on_id"),
    FOREIGN KEY ("product_id") REFERENCES "product" ("product_id") ON DELETE CASCADE,
    FOREIGN KEY ("variant_id") REFERENCES "variant" ("variant_id") ON DELETE CASCADE,
    FOREIGN KEY ("property_id") REFERENCES "property" ("property_id") ON DELETE CASCADE
);

-- Warehouse
DROP TABLE IF EXISTS "warehouse";
CREATE TABLE "warehouse" (
     "warehouse_id"  BIGSERIAL,
     "street_number" VARCHAR (10),
     "street_name"   VARCHAR (60),
     "city"          VARCHAR (40),
     "zipcode"       INTEGER,
     PRIMARY KEY ("warehouse_id")
);

-- Warehouse Contact
DROP TABLE IF EXISTS "warehouse_contact";
CREATE TABLE "warehouse_contact" (
	"telephone_number" VARCHAR (12),
	"warehouse_id"     BIGINT,
    PRIMARY KEY ("telephone_number"),
	FOREIGN KEY ("warehouse_id") REFERENCES "warehouse" ("warehouse_id") ON DELETE CASCADE
);

-- Inventory_
DROP TABLE IF EXISTS "inventory";
CREATE TABLE "inventory" (
    "warehouse_id" BIGINT,
    "variant_id"   BIGINT,
    "sku"          VARCHAR (20),
    "quantity"     INTEGER,
    PRIMARY KEY ("warehouse_id", "variant_id"),
    FOREIGN KEY ("warehouse_id") REFERENCES "warehouse" ("warehouse_id") ON DELETE CASCADE,
    FOREIGN KEY ("variant_id") REFERENCES "variant" ("variant_id") ON DELETE CASCADE
);

-- User
DROP TABLE IF EXISTS "user";
CREATE TABLE "user" (
    "user_id" BIGSERIAL,
    "role"    VARCHAR (10) CHECK ("role" IN ('GUEST_CUST', 'REG_CUST', 'ADMIN')),
    PRIMARY KEY ("user_id")
);

-- Registered User
DROP TABLE IF EXISTS "registered_user";
CREATE TABLE "registered_user" (
     "user_id"     BIGINT,
     "email"       VARCHAR (60) NOT NULL UNIQUE,
     "password"    VARCHAR (60) NOT NULL,
     "first_name"  VARCHAR (20),
     "last_name"   VARCHAR (20),
     "locked"      BOOLEAN NOT NULL,
     "enabled"     BOOLEAN NOT NULL,
     PRIMARY KEY ("user_id"),
     FOREIGN KEY ("user_id") REFERENCES "user" ("user_id") ON DELETE CASCADE
);

-- User Contact
DROP TABLE IF EXISTS "user_contact";
CREATE TABLE "user_contact" (
    "user_id"          BIGINT,
    "telephone_number" VARCHAR (12),
    PRIMARY KEY ("user_id", "telephone_number"),
    FOREIGN KEY ("user_id") REFERENCES "user" ("user_id") ON DELETE CASCADE
);

-- User ShippingAddress
DROP TABLE IF EXISTS "user_address";
CREATE TABLE "user_address" (
    "address_id"    BIGSERIAL,
    "user_id"       BIGINT,
    "street_number" VARCHAR (10),
    "street_name"   VARCHAR (60),
    "city"          VARCHAR (40),
    "zipcode"       INTEGER,
    PRIMARY KEY ("address_id"),
    FOREIGN KEY ("user_id") REFERENCES "user" ("user_id") ON DELETE CASCADE
);

-- Registered User Token
DROP TABLE IF EXISTS "token";
CREATE TABLE "token" (
    "user_id" BIGINT,
    "content" VARCHAR UNIQUE NOT NULL,
    "expired" BOOLEAN,
    "revoked" BOOLEAN,
    PRIMARY KEY ("content"),
    FOREIGN KEY ("user_id") REFERENCES "user" ("user_id") ON DELETE CASCADE
);

-- Cart
DROP TABLE IF EXISTS "cart";
CREATE TABLE "cart" (
     "user_id"     BIGINT,
     "total_price" NUMERIC (12, 2),
     PRIMARY KEY ("user_id"),
     FOREIGN KEY ("user_id") REFERENCES "user" ("user_id") ON DELETE CASCADE
);

-- Contains
DROP TABLE IF EXISTS "cart_item";
CREATE TABLE "cart_item" (
     "user_id"    BIGINT,
     "variant_id" BIGINT,
     "quantity"   INTEGER,
     PRIMARY KEY ("user_id", "variant_id"),
     FOREIGN KEY ("user_id") REFERENCES "cart" ("user_id") ON DELETE CASCADE,
     FOREIGN KEY ("variant_id") REFERENCES "variant" ("variant_id") ON DELETE CASCADE
);

-- Order
DROP TABLE IF EXISTS "order";
CREATE TABLE "order" (
    "order_id"        BIGSERIAL,

    "status"          VARCHAR (20) CHECK ("status" IN ('PLACED', 'PROCESSING', 'PROCESSED')),

    "date"            TIMESTAMP,
    "total_payment"   NUMERIC (12, 2),
    "payment_method"  VARCHAR (20),
    "delivery_method" VARCHAR (40),

    "customer_id"     BIGINT,
    "email"           VARCHAR (60),
    "street_number"   VARCHAR (10),
    "street_name"     VARCHAR (60),
    "city"            VARCHAR (40),
    "zipcode"         INTEGER,

    "telephone_number" VARCHAR (12),
    PRIMARY KEY ("order_id"),
    FOREIGN KEY ("customer_id") REFERENCES "user" ("user_id") ON DELETE NO ACTION
);

-- Order Item
DROP TABLE IF EXISTS "order_item";
CREATE TABLE "order_item" (
    "order_id"     BIGINT,
    "variant_id"   BIGINT,
    "warehouse_id" BIGINT,
    "quantity"     INTEGER,
    "price"        NUMERIC (10, 2),
    PRIMARY KEY ("order_id", "variant_id", "warehouse_id"),
    FOREIGN KEY ("order_id") REFERENCES "order" ("order_id") ON DELETE CASCADE,
    FOREIGN KEY ("variant_id") REFERENCES "variant" ("variant_id"),
    FOREIGN KEY ("warehouse_id") REFERENCES "warehouse" ("warehouse_id")
);

-- Sales Report
DROP TABLE IF EXISTS "sales_report";
CREATE TABLE "sales_report" (
    "year"           SMALLINT,
    "quarter"        SMALLINT CHECK ("quarter" IN (1, 2, 3, 4)),
    "total_sales"    INTEGER DEFAULT 0,
    "total_earnings" NUMERIC (10, 2) DEFAULT 0,
    PRIMARY KEY ("year", "quarter")
);

-- Sale
DROP TABLE IF EXISTS "sales_item";
CREATE TABLE "sales_item" (
    "year"       SMALLINT,
    "quarter"    SMALLINT,
    "variant_id" BIGINT,
    "sales"      INTEGER DEFAULT 0,
    "earnings"   NUMERIC (10, 2) DEFAULT 0,
    PRIMARY KEY ("year", "quarter", "variant_id"),
    FOREIGN KEY ("year", "quarter") REFERENCES "sales_report" ("year", "quarter") ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY ("variant_id") REFERENCES "variant" ("variant_id") ON DELETE NO ACTION ON UPDATE NO ACTION
);


------------------------------------------------------------------------------------------------------------------------
-- VIEWS----------------------------------------------------------------------------------------------------------------


DROP VIEW IF EXISTS "root_category";
CREATE VIEW "root_category" AS
SELECT *
FROM "category"
WHERE "category_id" NOT IN (SELECT DISTINCT "sub_category_id"
                            FROM sub_category);

-- SELECT *
-- FROM "root_category";

------------------------------------------------------------------------------------------------------------------------

/**
 * View: leaf_category
 *
 * This view selects categories that are considered leaf categories, meaning they are not subcategories of any other category.
 * It filters categories based on their "category_id" and their presence in the "sub_category" table.
 *
 * Categories included in this view are those with a "category_id" that exists in the "sub_category" table's "sub_category_id" column but does not exist in the "category_id" column of the "sub_category" table.
 */
DROP VIEW IF EXISTS "leaf_category";
CREATE VIEW "leaf_category" AS
    SELECT *
    FROM "category"
    WHERE "category_id" IN (SELECT DISTINCT "sub_category_id"
                        FROM sub_category) AND "category_id" NOT IN (SELECT DISTINCT "category_id"
	                                                                 FROM sub_category);

-- SELECT *
-- FROM "leaf_category";


------------------------------------------------------------------------------------------------------------------------
-- PROCEDURES-----------------------------------------------------------------------------------------------------------


CREATE OR REPLACE PROCEDURE "add_to_cart"(u_id BIGINT, v_id BIGINT, cnt INTEGER) AS $$
BEGIN
    IF NOT EXISTS (
        SELECT 1
        FROM cart_item
        WHERE user_id = u_id AND variant_id = v_id
    ) THEN
        INSERT INTO cart_item (user_id, variant_id, "quantity")
        VALUES (u_id, v_id, cnt);
    ELSE
        UPDATE cart_item
        SET "quantity" = "quantity" + cnt
        WHERE user_id = u_id AND variant_id = v_id;
    END IF;
END
$$ LANGUAGE plpgsql;

-- CALL "add_to_cart"(1, 1, 7);

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE PROCEDURE "stock"(
    w_id BIGINT,
    v_id BIGINT,
    sq VARCHAR (20),
    cnt INTEGER
) AS $$
BEGIN
    INSERT INTO "inventory"
    VALUES (w_id, v_id, sq, cnt);
EXCEPTION WHEN unique_violation THEN
    UPDATE "inventory"
    SET quantity = quantity + cnt
    WHERE "warehouse_id" = w_id AND "variant_id" = v_id;
END
$$ LANGUAGE plpgsql;

-- CALL "stock"(1, 3, 'SKU008', 100);

------------------------------------------------------------------------------------------------------------------------

-- Assuming that there is just one warehouse.

CREATE OR REPLACE PROCEDURE "update_inventory"(u_id BIGINT, o_id BIGINT) AS $$
DECLARE
    c_rec      RECORD;
    i_rec      RECORD;
    needed     INTEGER;
    v_price    NUMERIC (10, 2);
BEGIN
    FOR c_rec IN
        SELECT *
        FROM "cart_item"
        WHERE "user_id" = u_id
    LOOP
        SELECT "price" INTO v_price
        FROM "variant"
        WHERE "variant_id" = c_rec."variant_id";

        needed := c_rec."quantity";

        FOR i_rec IN
            SELECT *
            FROM "inventory"
            WHERE "variant_id" = c_rec."variant_id"
            ORDER BY "quantity" DESC
        LOOP
            IF needed <= 0 THEN
                -- The order has been fulfilled.
                EXIT;
            END IF;

            IF i_rec."quantity" >= needed THEN
                INSERT INTO "order_item"
                VALUES (o_id, i_rec."variant_id", i_rec."warehouse_id", needed, v_price * needed);

                UPDATE "inventory"
                SET "quantity" = "quantity" - needed
                WHERE ("warehouse_id", "variant_id") = (i_rec."warehouse_id", i_rec."variant_id");

                needed := 0;
            ELSE
                INSERT INTO "order_item"
                VALUES (o_id, i_rec."variant_id", i_rec."warehouse_id", i_rec."quantity", v_price * i_rec."quantity");

                UPDATE "inventory"
                SET "quantity" = 0
                WHERE ("warehouse_id", "variant_id") = (i_rec."warehouse_id", i_rec."variant_id");

                needed := needed - i_rec."quantity";
            END IF;
        END LOOP;

        IF needed > 0 THEN
            RAISE EXCEPTION 'For variant with id %, requested quantity: % exceeds available quantity: %.',
                c_rec."variant_id",
                c_rec."quantity",
                c_rec."quantity" - needed;
        END IF;
    END LOOP;
END
$$ LANGUAGE plpgsql;

CALL "update_inventory"(1, 1);


------------------------------------------------------------------------------------------------------------------------
-- FUNCTIONS------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION "buy_now"(u_id BIGINT, v_id BIGINT, qty INTEGER)
    RETURNS INTEGER AS $$
DECLARE
    needed  INTEGER := qty;
    o_id    BIGINT;
    i_rec   RECORD;
    v_price NUMERIC (10, 2);
BEGIN
    INSERT INTO "order" ("status", "date", "customer_id")
    VALUES ('PLACED', CURRENT_TIMESTAMP, u_id)
    RETURNING "order_id" INTO o_id;

    SELECT "price" INTO v_price
    FROM "variant"
    WHERE "variant_id" = v_id;

    FOR i_rec IN
        SELECT *
        FROM "inventory"
        WHERE "variant_id" = v_id
        ORDER BY "quantity" DESC
    LOOP
        IF needed <= 0 THEN
            -- The order has been fulfilled.
            EXIT;
        END IF;

        IF i_rec."quantity" >= needed THEN
            INSERT INTO "order_item"
            VALUES (o_id, v_id, i_rec."warehouse_id", needed, v_price * needed);

            UPDATE "inventory"
            SET "quantity" = "quantity" - needed
            WHERE ("warehouse_id", "variant_id") = (i_rec."warehouse_id", v_id);

            needed := 0;
        ELSE
            INSERT INTO "order_item"
            VALUES (o_id, v_id, i_rec."warehouse_id", i_rec."quantity", v_price * i_rec."quantity");

            UPDATE "inventory"
            SET "quantity" = 0
            WHERE ("warehouse_id", "variant_id") = (i_rec."warehouse_id", i_rec."variant_id");

            needed := needed - i_rec."quantity";
        END IF;
    END LOOP;

    RETURN o_id;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "buy_now"(1, 104, 120);

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "categories_from_product"(p_id BIGINT)
    RETURNS TABLE (
        "category_id"          BIGINT,
        "category_name"        VARCHAR (40),
        "category_description" VARCHAR
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT DISTINCT c.*
        FROM "category" AS c NATURAL LEFT OUTER JOIN "belongs_to" AS bt NATURAL LEFT OUTER JOIN "product" AS p
        WHERE p."product_id" = p_id;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "categories_from_product"(1);

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "categories_with_most_orders"()
    RETURNS TABLE (
        "category_id"          BIGINT,
        "category_name"        VARCHAR (40),
        "category_description" VARCHAR,
        "order_count"          INTEGER
    ) AS $$
BEGIN
    RETURN QUERY
        WITH ranked_categories AS (
            SELECT
                lc."category_id",
                lc."category_name",
                lc."category_description",
                CAST(COUNT(DISTINCT o."order_id") AS INTEGER) AS "order_count",
                RANK() OVER (ORDER BY COUNT(DISTINCT o."order_id") DESC) AS rank
            FROM
                "leaf_category" AS lc
                    NATURAL LEFT OUTER JOIN
                "belongs_to" AS bt
                    NATURAL LEFT OUTER JOIN
                "varies_on" AS vo
                    NATURAL LEFT OUTER JOIN
                "order_item" AS oi
                    NATURAL LEFT OUTER JOIN
                "order" AS o
            GROUP BY
                lc."category_id",
                lc."category_name",
                lc."category_description"
        )
        SELECT
            rc."category_id",
            rc."category_name",
            rc."category_description",
            rc."order_count"
        FROM
            ranked_categories AS rc
        WHERE rank = 1;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "categories_with_most_orders"();

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "count_stocks"(p_id BIGINT)
    RETURNS INTEGER AS $$
DECLARE
    stock_count INTEGER;
BEGIN
    SELECT SUM(quantity) INTO stock_count
    FROM "varies_on" AS vo NATURAL LEFT OUTER JOIN "inventory" AS i
    WHERE vo."product_id" = p_id;

    IF stock_count NOTNULL
    THEN RETURN stock_count;
    ELSE RETURN 0;
    END IF;
END
$$ LANGUAGE plpgsql;

-- SELECT count_stocks(2);

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "customer_order_report"(c_id BIGINT)
    RETURNS TABLE (
        "order_id"      BIGINT,
        "date"          TIMESTAMP,
        "total_payment" NUMERIC (12, 2),
        "variant_id"    BIGINT,
        "count"         INTEGER
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT o."order_id", o."date", o."total_payment", oi."variant_id", oi.quantity
        FROM "order" AS o NATURAL LEFT OUTER JOIN "order_item" AS oi
        WHERE o."customer_id" = c_id;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "customer_order_report"(1);

------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS "images_from_product";
CREATE OR REPLACE FUNCTION "images_from_product"(p_id BIGINT)
    RETURNS TABLE (
        "product_id" BIGINT,
        "url"      VARCHAR (100)
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT DISTINCT i.*
        FROM "image" AS i NATURAL LEFT OUTER JOIN "product" AS p
        WHERE p."product_id" = p_id;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "images_from_product"(1);

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "products_from_category"(c_id BIGINT)
    RETURNS TABLE (
        "product_id"   BIGINT,
        "product_name" VARCHAR(100),
        "base_price"   NUMERIC(10, 2),
        "brand"        VARCHAR(40),
        "description"  VARCHAR,
        "image_url"    VARCHAR(100)
    ) AS $$
BEGIN
    CREATE TEMP TABLE "mappings" (
        "category_id" BIGINT,
        "product_id" BIGINT
    );

    INSERT INTO "mappings"
        SELECT *
        FROM "belongs_to"
        WHERE "category_id" = c_id;

    INSERT INTO "mappings"
        SELECT sc."sub_category_id", bt."product_id"
        FROM "sub_category" AS sc RIGHT OUTER JOIN "belongs_to" AS bt ON sc."sub_category_id" = bt."category_id"
        WHERE sc."category_id" = c_id;

    RETURN QUERY
        SELECT DISTINCT p.*
        FROM "mappings" NATURAL LEFT OUTER JOIN "product" AS p;

    DROP TABLE IF EXISTS "mappings";
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "products_from_category"(1);

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "products_with_most_sales"(y SMALLINT, q SMALLINT)
    RETURNS TABLE(
        "product_id"     BIGINT,
        "total_sales"    INTEGER,
        "total_earnings" NUMERIC (10, 2)
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT vo."product_id",
               SUM(si."sales") AS "total_sales",
               SUM(si."earnings") AS "total_earnings"
        FROM "varies_on" AS vo NATURAL RIGHT OUTER JOIN "sales_item" si
        WHERE (si."year", si."quarter") = (y, q)
        GROUP BY (vo."product_id")
        ORDER BY ("total_sales", "total_earnings") DESC;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM products_with_most_sales(2022, 'Q1');

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "properties_from_product"(p_id BIGINT)
    RETURNS TABLE (
        "property_id"     BIGINT,
        "property_name"   VARCHAR (40),
        "value"           VARCHAR (40),
        "image_url"       VARCHAR (400),
        "price_increment" NUMERIC (10, 2)
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT DISTINCT pp.*
        FROM "property" AS pp NATURAL LEFT OUTER JOIN "varies_on" AS vo LEFT OUTER JOIN "product" AS pd ON vo."product_id" = pd."product_id"
        WHERE pd."product_id" = p_id
        ORDER BY pp."property_name" DESC;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "properties_from_product"(14);

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "quarters_with_most_interest"(p_id BIGINT)
    RETURNS TABLE (
        "year"     SMALLINT,
        "quarter"  SMALLINT,
        "total_sales"    INTEGER,
        "total_earnings" NUMERIC(10, 2)
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT si."year",
               si."quarter",
               SUM(si."sales") AS "total_sales",
               SUM(si."earnings") AS "total_earnings"
        FROM "varies_on" AS vo NATURAL RIGHT OUTER JOIN "sales_item" si
        WHERE vo."product_id" = p_id
        GROUP BY ("year", "quarter")
        ORDER BY ("total_sales", "total_earnings") DESC;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "time_period_with_most_interest"(1);

------------------------------------------------------------------------------------------------------------------------

DROP FUNCTION IF EXISTS "search_products_by_name"();
CREATE OR REPLACE FUNCTION "search_products_by_name"(p_name VARCHAR (100))
    RETURNS TABLE (
        "product_id"   BIGINT,
        "product_name" VARCHAR (100),
        "base_price"   NUMERIC (10, 2),
        "brand"        VARCHAR (40),
        "description"  VARCHAR,
        "main_image"   VARCHAR (100)
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT *
        FROM "product" AS p
        WHERE p."product_name" ILIKE CONCAT('%', p_name, '%');
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "search_product_by_name"('phone');

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "rank_categories_by_orders"()
    RETURNS TABLE (
        "category_id"   BIGINT,
        "order_count"   INTEGER
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT lc.category_id, COUNT(DISTINCT oi.order_id) AS order_count
        FROM "leaf_category" AS lc
            NATURAL LEFT OUTER JOIN "belongs_to" AS bt
            NATURAL LEFT OUTER JOIN "varies_on" AS vo
            NATURAL LEFT OUTER JOIN "order_item" AS oi
        GROUP BY lc."category_id";
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "rank_categories_by_orders"();

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "unmarketable_properties"(p_id BIGINT)
    RETURNS TABLE (
        "property_id"     BIGINT,
        "property_name"   VARCHAR (40),
        "value"           VARCHAR (40),
        "image_url"       VARCHAR (100),
        "price_increment" NUMERIC (10, 2)
    ) AS $$
BEGIN
    RETURN QUERY
        SELECT DISTINCT pp.*
        FROM "varies_on" vo NATURAL RIGHT OUTER JOIN "property" AS pp
        WHERE vo."product_id" = p_id AND pp."price_increment" = 0
        ORDER BY pp."property_name" ASC, pp."value" ASC;
END
$$ LANGUAGE plpgsql;

-- SELECT *
-- FROM "unmarketable_properties"(1);

------------------------------------------------------------------------------------------------------------------------
-- Triggers-------------------------------------------------------------------------------------------------------------


CREATE OR REPLACE FUNCTION "delete_order_items"() RETURNS TRIGGER AS $$
    DECLARE row_record "order_item"%ROWTYPE;
    DECLARE query TEXT;
BEGIN
    query := 'SELECT * ' ||
             'FROM "order_item" ' ||
             'WHERE "order_id" = ' || OLD."order_id";

    IF OLD."status" = 'PLACED' THEN
        FOR row_record IN EXECUTE query LOOP
            UPDATE "inventory" AS i
            SET quantity = quantity + row_record.quantity
            WHERE (i."warehouse_id", i."variant_id") = (row_record."warehouse_id", row_record."variant_id");
        END LOOP;
    END IF;

    RETURN OLD;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS "delete_order_items" ON "order";
CREATE TRIGGER "delete_order_items"
    BEFORE DELETE ON "order"
    FOR EACH ROW
EXECUTE FUNCTION "delete_order_items"();

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "update_cart"() RETURNS TRIGGER AS $$
    DECLARE variantPrice NUMERIC (10, 2);
BEGIN
    variantPrice := 0;

    SELECT v."price" INTO variantPrice
    FROM "cart_item" AS ci NATURAL LEFT OUTER JOIN "variant" AS v
    WHERE (ci."user_id", ci."variant_id") = (NEW."user_id", NEW."variant_id");

    IF (TG_OP = 'INSERT') THEN
        UPDATE "cart"
        SET "total_price" = "total_price" + variantPrice * NEW."count"
        WHERE "user_id" = NEW."user_id";
    ELSIF (TG_OP = 'UPDATE') THEN
        UPDATE "cart"
        SET "total_price" = "total_price" + variantPrice * NEW."count" - OLD."count"
        WHERE "user_id" = NEW."user_id";
    END IF;

    RETURN NEW;
END
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS "update_cart" ON "cart_item";
CREATE TRIGGER "update_cart"
    AFTER INSERT OR UPDATE ON "cart_item"
    FOR EACH ROW
EXECUTE FUNCTION "update_cart"();

------------------------------------------------------------------------------------------------------------------------

CREATE OR REPLACE FUNCTION "update_variant"() RETURNS TRIGGER AS $$
DECLARE productPrice NUMERIC (10, 2);
    DECLARE propertyPrice NUMERIC (10, 2);
    DECLARE variantPrice NUMERIC (10, 2);
BEGIN
    productPrice := 0;
    propertyPrice := 0;
    variantPrice := 0;

    SELECT "price" INTO variantPrice
    FROM "variant"
    WHERE "variant_id" = NEW."variant_id";

    IF variantPrice = 0 THEN
        SELECT "base_price" INTO productPrice
        FROM "product"
        WHERE "product_id" = NEW."product_id";

        UPDATE "variant"
        SET "price" = productPrice
        WHERE "variant_id" = NEW."variant_id";
    END IF;

    SELECT "price_increment" INTO propertyPrice
    FROM "property"
    WHERE "property_id" = NEW."property_id";

    UPDATE "variant"
    SET "price" = "price" + propertyPrice
    WHERE "variant_id" = NEW."variant_id";

    RETURN NEW;
END;
$$ LANGUAGE plpgsql;

DROP TRIGGER IF EXISTS "update_variant" ON "varies_on";
CREATE TRIGGER "update_variant"
    AFTER INSERT ON "varies_on"
    FOR EACH ROW
EXECUTE FUNCTION "update_variant"();
