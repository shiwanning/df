package com.tcg.mis.common.constant;


import java.util.HashMap;
import java.util.Map;

public class UriPermission {

    private static final Map<String, Integer> urlPermissionMap;
    private static final Map<String, Integer> urlPostPermissionMap;
    private static final Map<String, Integer> urlPutPermissionMap;
    private static final Map<String, Integer> urlDeletePermissionMap;
    
    static {
        urlPermissionMap = new HashMap<>(97);
        urlPostPermissionMap = new HashMap<>(97);
        urlPutPermissionMap = new HashMap<>(97);
        urlDeletePermissionMap = new HashMap<>(97);
        
        urlPermissionMap.put("/tcg-mis-console/bank/account/create", 90021);
        urlPermissionMap.put("/tcg-mis-console/bank/account/view", 90022);
        urlPermissionMap.put("/tcg-mis-console/bank/account", 90023);
        urlPermissionMap.put("/tcg-mis-console/bank/account/update", 90024);
        urlPermissionMap.put("/tcg-mis-console/recharge/transaction", 90061);
        urlPermissionMap.put("/tcg-mis-console/recharge/transaction/approve", 90062);
        urlPermissionMap.put("/tcg-mis-console/recharge/p/transaction", 90101);
        urlPermissionMap.put("/tcg-mis-console/recharge/p/transaction/approve", 90102);
        urlPermissionMap.put("/tcg-mis-console/withdraw/transaction", 90081);
        urlPermissionMap.put("/tcg-mis-console/withdraw/transaction/approve", 90082);
        urlPermissionMap.put("/tcg-mis-console/product/wallet/recharge", 90141);
        urlPermissionMap.put("/tcg-mis-console/product/wallet/transfer", 90142);
        urlPermissionMap.put("/tcg-mis-console/product/wallet/p/transfer", 90143);
        urlPermissionMap.put("/tcg-mis-console/product/wallet/transaction", 90161);
        urlPermissionMap.put("/tcg-mis-console/transaction/tcg", 90127);
        urlPermissionMap.put("/tcg-mis-console/transaction/merchant", 90201);
        urlPermissionMap.put("/tcg-mis-console/transaction/p/merchant", 90181);
        urlPermissionMap.put("/tcg-mis-console/product/wallet/fund/transaction", 90221);
        urlPermissionMap.put("/tcg-mis-console/product/wallet/fund/approve", 90222);
        urlPermissionMap.put("/tcg-mis-console/product/wallet/fund/reject", 90223);
        
        // 商户费率
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/template/list", 11101);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/template", 11104);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/template/merchants", 11105);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/review/list", 11201);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/review/approve", 11202);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/review/reject", 11203);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/review/diff", 11204);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/credit/review/list", 11211);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/credit/review/approve", 11212);
        urlPermissionMap.put("/tcg-mis-console/merchant/charge/credit/review/reject", 11213);
        
        urlPostPermissionMap.put("/tcg-mis-console/merchant/charge/template", 11102);
        urlPutPermissionMap.put("/tcg-mis-console/merchant/charge/template", 11103);
    }

    public static boolean hasURIMapping(String url) {
        return urlPermissionMap.containsKey(url);
    }

    public static Integer getMenuId(String url) {
        return urlPermissionMap.get(url);
    }

	public static Integer getMenuId(String url, String method) {
		Integer menuId = null;
		switch(method) {
		    case "POST": menuId = urlPostPermissionMap.get(url); break;
		    case "PUT": menuId = urlPutPermissionMap.get(url); break;
		    case "DELETE": menuId = urlDeletePermissionMap.get(url); break;
		}
		
		if(menuId == null) {
			menuId = getMenuId(url);
		}
		
		return menuId;
	}
}
