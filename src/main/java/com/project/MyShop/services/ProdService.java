package com.project.MyShop.services;

import com.project.MyShop.models.Image;
import com.project.MyShop.models.Product;
import com.project.MyShop.models.User;
import com.project.MyShop.repositories.ProdRepo;
import com.project.MyShop.repositories.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ProdService {
    private final ProdRepo prodRepo;
    private final UserRepo userRepo;

    public List<Product> listProd(String searchWord) {
        List<Product> products = prodRepo.findAll();
        if (searchWord.equals("")) {
            return products;
        }
        if (!searchWord.equals("")) {
            products = searchWord(searchWord, products);
        }
        return products;
    }

    public void saveProd(
            Principal principal,
            Product prod,
            MultipartFile file1,
            MultipartFile file2,
            MultipartFile file3
    ) throws IOException {
        prod.setUser(getUserByPrincipal(principal));
        Image image1;
        Image image2;
        Image image3;
        if (file1.getSize() != 0) {
            image1 = toImageEntity(file1);
            image1.setPreviewImage(true);
            prod.addImageToProd(image1);
        }
        if (file2.getSize() != 0) {
            image2 = toImageEntity(file2);
            prod.addImageToProd(image2);
        }
        if (file3.getSize() != 0) {
            image3 = toImageEntity(file3);
            prod.addImageToProd(image3);
        }
        Product prodFromDb = prodRepo.save(prod);
        prodFromDb.setPreviewImageId(prodFromDb.getImages().get(0).getId());
        prodRepo.save(prod);
    }

    public User getUserByPrincipal(Principal principal) {
        if (principal == null) {
            return new User();
        }
        return userRepo.findByEmail(principal.getName());
    }

    private Image toImageEntity(MultipartFile file) throws IOException {
        Image image = new Image();
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        image.setBytes(file.getBytes());
        return image;
    }

    public void deleteProd(Long id) {
        prodRepo.deleteById(id);
    }

    public Product getProdById(Long id) {
        return prodRepo.findById(id).orElse(null);
    }

    private List<Product> searchWord(String searchWord, List<Product> products) {
        List<Product> searchProducts = new ArrayList<>();
        String lowerSearchWord = makeStringToLowerCase(searchWord);
        char[] searchWordToCharArray = lowerSearchWord.toCharArray();
        for (int a = 0; a < products.size(); a++) {
            String lowerProductName = makeStringToLowerCase(products.get(a).getTitle());
            char[] chars = lowerProductName.toCharArray();
            for (int i = 0; i < chars.length; i++) {
                for (int j = 0; j < searchWordToCharArray.length; j++) {
                    try {
                        if (chars[i] == searchWordToCharArray[j]) {
                            if (chars[i + 1] == searchWordToCharArray[j + 1]) {
                                if (chars[i + 2] == searchWordToCharArray[j + 2]) {
                                        if (!searchProducts.contains(products.get(a))) {
                                            searchProducts.add(products.get(a));
                                            break;
                                        }
                                }
                            }
                        }
                    } catch (IndexOutOfBoundsException e) {
                        break;
                    }
                }
            }
        }
        return searchProducts;
    }


    private String makeStringToLowerCase(String word) {
        StringBuilder lowerString = new StringBuilder();
        for (int i = 0; i < word.length(); i++) {
            char c = word.charAt(i);
            lowerString.append(Character.toLowerCase(c));
        }
        return lowerString.toString();
    }
}
