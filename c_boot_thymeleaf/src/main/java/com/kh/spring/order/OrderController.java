package com.kh.spring.order;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("order")
@RequiredArgsConstructor
public class OrderController {

   @Autowired
   private final OrderService orderService;
   
   @GetMapping("order-form")
   public void orderForm() {}
   
   @PostMapping("order")
   public String order(@RequestParam(required = false, name = "food") 
                  Optional<List<String>> foods   //Optional : null일 때 예외처리 해주는 
                  ,Model model) {
	   
	  //Optional을 사용하지 않고 List<String>으로 매개변수를 전달 받을 때 코드
	  //foods = foods == null? new ArrayList<String>() : foods;
      
	  Map<String,Object> commandMap = orderService.order(foods.orElseGet(() -> List.of())); //null인 경우, 빈 list 반환, null아닌 경우에는 넘어온 값이 반환
      model.addAllAttributes(commandMap);
      return "order/receipt";
   }
   
   
   
   
   
   
   
   
   
   
   
   
}