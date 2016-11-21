<!DOCTYPE html>
<html>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<#assign listType = Request['type']>

<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <div class="tab">
            <ul>
                    <#-- 只要type不是0不是买家爱的话，都聚焦在所有内容上 -->
                <li <#if listType?? &&listType != 0>class="z-sel"</#if> ><a href="${base}/?type=1">所有内容</a></li>
                    <#-- 只要是买家就提供未购买选项，聚焦在未购买产品上 -->
                <#if user?? && user.userType == 0><li <#if listType == 0>class="z-sel"</#if> ><a href="${base}/">未购买的内容</a></li></#if>
            </ul>
        </div>
    </div>
    
    <#-- 判断产品list是否有内容 -->
    <#if !productList?? || !productList?has_content>
    <div class="n-result">
        <h3>暂无内容！</h3>
    </div>
    <#else>
    <div class="n-plist">
        <ul class="f-cb" id="plist">
        <#-- 有内容的话进一步判断user是否存在及user类别，有user且是买家且可以查看未购买内容 -->
        <#if user?? && user.userType == 0 && listType == 0>
            <#list productList as x>
                <#if !x.isBuy>
                <li id="p-${x.id}">
                    <a href="${base}/show?id=${x.id}" class="link">
                        <div class="img"><img src="${x.image}"></div>
                        <h3>${x.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price*0.01}</span></div>
                    </a>
                </li>
                </#if>
            </#list>
            <#-- 不符合上述条件的话，继续细分 -->
        <#else>
            <#list productList as x>
                <li id="p-${x.id}">
                    <a href="${base}/show?id=${x.id}" class="link">
                        <div class="img"><img src="${x.image}" alt="${x.title}"></div>
                        <h3>${x.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price*0.01}</span></div>
                        <#-- 有user的话，买家显示已购买-->
                        <#if user?? && user.userType==0 && x.isBuy><span class="had"><b>已购买</b></span></#if>
                         <#-- 有user的话，卖家显示已售出-->
                        <#if user?? && user.userType==1 && x.isSell><span class="had"><b>已售出</b></span></#if>
                        <#-- 有user的话，卖家可以删除未售出-->
                    </a>
                    <#if user?? && user.userType==1 && !x.isSell><span class="u-btn u-btn-normal u-btn-xs del" data-del="${x.id}">删除</span></#if>
                </li>
            </#list>
        </#if>
        </ul>
    </div>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="${base}/js/global.js"></script>
<script type="text/javascript" src="${base}/js/pageIndex.js"></script>
</body>
</html>