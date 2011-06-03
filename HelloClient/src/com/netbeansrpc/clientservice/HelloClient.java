/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.netbeansrpc.clientservice;

import com.netbeansrpc.helloservice.HelloService;

/**
 *
 * @author gperon
 */
public class HelloClient {
    public static void main(String[] args) {
        new HelloService().hello("Netbeans");
    }
}
