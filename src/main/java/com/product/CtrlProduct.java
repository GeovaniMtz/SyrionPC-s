package com.product;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CtrlProduct {

    @GetMapping("/category")
    public Category[] getCategories() {

        Category c1 = new Category();
        c1.setCategory_id(1);
        c1.setCategory("ProcesadoresAMD");
        c1.setTag("PAMD");
        c1.setStatus(1);

        Category c2 = new Category();
        c2.setCategory_id(2);
        c2.setCategory("ProcesadoresIntel");
        c2.setTag("PINTL");
        c2.setStatus(1);

        return new Category[]{ c1, c2 };
    }
}
