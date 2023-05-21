package com.cybersoft.demospringsecurity.filter;

import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@Component

// OncePerRequestFilter : có request yêu cầu chứng thực thì chạy vào filter
public class JwtFilter extends OncePerRequestFilter {

    //Bước 1: Lấy token
    //Bước 2: Giải mã token
    //Bước 3: Token hợp lệ tạo chứng thực cho security
    // những key cần chứng thực thì truyền authorization và bearer token lên

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        // chỗ này lấy token bằng authorization trong headers
        // request.getHeader("keyname") để lấy value của key đó
        String header = request.getHeader("Authorization");
        System.out.println("Kiểm tra:" + header);


        // cho phép đi vào link mà user muốn access
        filterChain.doFilter(request,response);
    }
}
