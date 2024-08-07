package org.ypolin.kata;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * https://www.codewars.com/kata/563fbac924106b8bf7000046/java
 *
 * Breadcrumb Generator
 *
 * As breadcrumb menùs are quite popular today, I won't digress much on explaining them, leaving the wiki link to do all the dirty work in my place.
 *
 * What might not be so trivial is instead to get a decent breadcrumb from your current url. For this kata, your purpose is to create a function that takes a url, strips the first part (labelling it always HOME) and then builds it making each element but the last a <a> element linking to the relevant path; last has to be a <span> element getting the active class.
 *
 * All elements need to be turned to uppercase and separated by a separator, given as the second parameter of the function; the last element can terminate in some common extension like .html, .htm, .php or .asp; if the name of the last element is index.something, you treat it as if it wasn't there, sending users automatically to the upper level folder.
 *
 * A few examples can be more helpful than thousands of words of explanation, so here you have them:
 *
 * Solution.generate_bc("mysite.com/pictures/holidays.html", " : ").equals( '<a href="/">HOME</a> : <a href="/pictures/">PICTURES</a> : <span class="active">HOLIDAYS</span>' );
 * Solution.generate_bc("www.codewars.com/users/GiacomoSorbi", " / ").equals( '<a href="/">HOME</a> / <a href="/users/">USERS</a> / <span class="active">GIACOMOSORBI</span>' );
 * Solution.generate_bc("www.microsoft.com/docs/index.htm", " * ").equals( '<a href="/">HOME</a> * <span class="active">DOCS</span>' );
 * Seems easy enough?
 *
 * Well, probably not so much, but we have one last extra rule: if one element (other than the root/home) is longer than 30 characters, you have to shorten it, acronymizing it (i.e.: taking just the initials of every word); url will be always given in the format this-is-an-element-of-the-url and you should ignore words in this array while acronymizing: ["the","of","in","from","by","with","and", "or", "for", "to", "at", "a"]; a url composed of more words separated by - and equal or less than 30 characters long needs to be just uppercased with hyphens replaced by spaces.
 *
 * Ignore anchors (www.url.com#lameAnchorExample) and parameters (www.url.com?codewars=rocks&pippi=rocksToo) when present.
 *
 * Examples:
 *
 * Solution.generate_bc("mysite.com/very-long-url-to-make-a-silly-yet-meaningful-example/example.htm", " > ").equals( '<a href="/">HOME</a> > <a href="/very-long-url-to-make-a-silly-yet-meaningful-example/">VLUMSYME</a> > <span class="active">EXAMPLE</span>' );
 * Solution.generate_bc("www.very-long-site_name-to-make-a-silly-yet-meaningful-example.com/users/giacomo-sorbi", " + ").equals( '<a href="/">HOME</a> + <a href="/users/">USERS</a> + <span class="active">GIACOMO SORBI</span>' );
 * You will always be provided valid url to webpages in common formats, so you probably shouldn't bother validating them.
 */
public class BreadcrumbGeneratorSolution {
    private static List ignoreWords = Arrays.asList("the","of","in","from","by","with","and", "or", "for", "to", "at", "a");
    public static String generate_bc(String url, String separator) {
        List<String> urlParts = new ArrayList<>(Arrays.asList(url.split("\\/")));
        urlParts.removeIf(s -> s.isBlank() || s.matches("[^a-zA-Z0-9]+")
                || s.matches("index\\..+") || s.startsWith("http") || s.matches("[\\#|\\?|\\.].+"));

        List<String> crumbs = new ArrayList<>();
        for (int i = 0; i < urlParts.size(); i++) {
            String s = urlParts.get(i);
            if (i == 0 && i != urlParts.size() - 1) {
                crumbs.add("<a href=\"/\">HOME</a>");
            }
            if (i == urlParts.size() - 1) {
                String title = i == 0 ? "HOME" : formatTitle(s.replace("[\\#|\\?|\\.].+", ""));
                crumbs.add(String.format("<span class=\"active\">%s</span>", title));
            }
            if (i > 0 && i != urlParts.size() - 1) {
                String path = i>2? urlParts.subList(1, i).stream().collect(Collectors.joining("/")): s;
                crumbs.add(String.format("<a href=\"/%s/\">%s</a>", path, formatTitle(s)));
            }
        }
        return crumbs.stream().collect(Collectors.joining(separator));
    }

    private static String formatTitle (String value){
        if(!value.contains("-")){
            return value.toUpperCase();
        }
        if (value.length() <= 30) {
            return Arrays.stream(value.split("-")).map(String::toUpperCase).collect(Collectors.joining(" "));
        } else {
            return Arrays.stream(value.split("-"))
                    .filter(s -> !ignoreWords.contains(s))
                    .map(s -> Character.toString(s.charAt(0)).toUpperCase())
                    .collect(Collectors.joining());
        }
    }
}