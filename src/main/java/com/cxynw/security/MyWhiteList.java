package com.cxynw.security;

import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Element;
import org.jsoup.safety.Whitelist;

/**
 * Jsoup提供的relaxed白名单，完全不够用，问题不断，没办法，只能自己自定义白名单
 */
public class MyWhiteList extends Whitelist {

    public static Whitelist relaxed() {
        return new MyWhiteList()
                .addTags(
                        "a", "b", "blockquote", "br", "caption", "cite", "code", "col",
                        "colgroup", "dd", "div", "dl", "dt", "em", "h1", "h2", "h3", "h4", "h5", "h6",
                        "i", "img", "li", "ol", "p", "pre", "q", "small", "span", "strike", "strong",
                        "sub", "sup", "table", "tbody", "td", "tfoot", "th", "thead", "tr", "u",
                        "ul","xmp","font","hr")

                .addAttributes("pre","type")
                .addAttributes("span","style")
                .addAttributes("p","style","face")
                .addAttributes("font","color","size")
                .addAttributes("a", "href", "title", "target", "style")
                .addAttributes("blockquote", "cite")
                .addAttributes("col", "span", "width")
                .addAttributes("colgroup", "span", "width")
                .addAttributes("img", "align", "alt", "height", "src", "title", "width", "style")
                .addAttributes("ol", "start", "type")
                .addAttributes("q", "cite")
                .addAttributes("table", "summary", "width", "style", "border")
                .addAttributes("td", "abbr", "axis", "colspan", "rowspan", "width", "style")
                .addAttributes("tr", "style")
                .addAttributes("th", "abbr", "axis", "colspan", "rowspan", "scope", "width")
                .addAttributes("ul", "type")

                .addProtocols("a", "href", "ftp", "http", "https", "mailto")
                .addProtocols("blockquote", "cite", "http", "https")
                .addProtocols("cite", "cite", "http", "https")
                .addProtocols("img", "src", "http", "https")
                .addProtocols("q", "cite", "http", "https")
                ;
    }


    @Override
    protected boolean isSafeAttribute(String tagName, Element el, Attribute attr) {
        boolean isImg = tagName.equalsIgnoreCase("img");
        boolean isSrc = attr.getKey().equalsIgnoreCase("src");
        if(isImg && isSrc){
            return true;
        }
        return super.isSafeAttribute(tagName, el, attr);
    }

}
