package ahea.hackerton.nurikabe.crawler.puzzle;

import java.util.ArrayList;
import java.util.List;

public class PuzzleParser {

    public class String parser(String crwalData){

        List<List<java.lang.String>> result = new ArrayList<>();

        java.lang.String tempTopPx = null;
        java.lang.String newTopPx = null;
        List<java.lang.String> list = new ArrayList<>();
        for(java.lang.String raw : crwalData.split("div class")){

            if(raw.contains("cell selectable cell-off")){
                newTopPx = getTopPx(raw);
                if(tempTopPx != null){
                    if(!(Long.parseLong(tempTopPx)-2 <= Long.parseLong(newTopPx)
                        && Long.parseLong(tempTopPx)+2 >= Long.parseLong(newTopPx))){
                        tempTopPx = newTopPx;
                        result.add(list);
                        list = new ArrayList<>();
                    }
                }else{
                    tempTopPx = newTopPx;
                }
                list.add("-1");
            }else if(raw.contains("nurikabe-task-cell wall")){
                newTopPx = getTopPx(raw);
                if(tempTopPx != null){
                    if(!(Long.parseLong(tempTopPx)-2 <= Long.parseLong(newTopPx)
                        && Long.parseLong(tempTopPx)+2 >= Long.parseLong(newTopPx))){
                        tempTopPx = newTopPx;
                        result.add(list);
                        list = new ArrayList<>();
                    }
                }else{
                    tempTopPx = newTopPx;
                }
                list.add("0");
            }else if(raw.contains("nurikabe-task-cell")){
                newTopPx = getTopPx(raw);
                if(tempTopPx != null){
                    if(!(Long.parseLong(tempTopPx)-2 <= Long.parseLong(newTopPx)
                        && Long.parseLong(tempTopPx)+2 >= Long.parseLong(newTopPx))){
                        tempTopPx = newTopPx;
                        result.add(list);
                        list = new ArrayList<>();
                    }
                }else{
                    tempTopPx = newTopPx;
                }
                list.add(raw.substring(raw.indexOf("</div")-1,raw.indexOf("</div")));
            }
        }
        return result.toString();
    }
    private static java.lang.String getTopPx(java.lang.String raw) {
        return raw.substring(raw.indexOf("top")+5,raw.indexOf("px;")).trim();
    }
}
