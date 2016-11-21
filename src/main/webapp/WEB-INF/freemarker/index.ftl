<!DOCTYPE html>
<html>
<link rel="stylesheet" href=".style.css"/>
<#include "/include/head.ftl">
<body>
<#include "/include/support.ftl">
<#include "/include/header.ftl">
<#assign listType = Request['type']>


<div class="g-doc">
    <div class="m-tab m-tab-fw m-tab-simple f-cb">
        <div class="tab">
            <ul>
                <li <#if !listType?? || listType != 1>class="z-sel"</#if> ><a href="http://localhost:8080/lesson7/">所有内容</a></li>
                <#if user?? && user.userType == 0><li <#if listType == 1>class="z-sel"</#if> ><a href="http://localhost:8080/lesson7/?type=1">未购买的内容</a></li></#if>
            </ul>
        </div>
    </div>
    <#if !productList?? || !productList?has_content>
    <div class="n-result">
        <h3>暂无内容！</h3>
    </div>
    <#else>
    <div class="n-plist">
        <ul class="f-cb" id="plist">
        <#if user?? && user.userType == 0 && listType == 1>
            <#list productList as x>
          
                <li id="p-${x.id}">
                    <a href="http://localhost:8080/lesson7//show?id=${x.id}" class="link">
                        <div class="img">< alt="${x.title}"></div>
                        <h3>${x.title}</h3>
                        <div class="price"><span class="v-unit">¥</span><span class="v-value">${x.price}</span></div>
                    </a>
                </li>
               
            </#list>
             </#if>
    </#if>
</div>
<#include "/include/footer.ftl">
<script type="text/javascript" src="./js/global.js"></script>
<script type="text/javascript" src="./js/pageIndex.js"></script>
</body>
</html>