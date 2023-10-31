package com.cstore.dao.image;

import com.cstore.model.product.Image;
import org.springframework.dao.DataAccessException;

import java.util.List;

public interface ImageDao {
    List<Image> findByProductId(Long productId);

    Image save(Image image) throws DataAccessException;
}
