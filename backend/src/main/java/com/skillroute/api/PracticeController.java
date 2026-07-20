package com.skillroute.api;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController @RequestMapping("/api/v1")
@CrossOrigin(origins={"http://localhost:3000","http://localhost:8888"})
public class PracticeController {
  private final MasteryService mastery;
  PracticeController(MasteryService mastery){this.mastery=mastery;}
  @GetMapping("/learners/{learnerId}/next")
  Map<String,Object> next(@PathVariable String learnerId){
    return Map.of("questionId","bs-boundaries-01","title","Find First and Last Position","topic","Binary Search","difficulty","MEDIUM","mastery",mastery.score(learnerId,"binary-search"),"reason","Boundary handling is the weakest prerequisite signal.");
  }
  @GetMapping("/learners/{learnerId}/path")
  List<Map<String,Object>> path(@PathVariable String learnerId){
    return List.of(topic("arrays","Arrays & Strings",100,true,List.of()),topic("binary-search","Binary Search",mastery.score(learnerId,"binary-search"),true,List.of("arrays")),topic("two-pointers","Two Pointers",0,mastery.score(learnerId,"binary-search")>=75,List.of("binary-search")));
  }
  @PostMapping("/submissions")
  Map<String,Object> submit(@Valid @RequestBody Submission request){
    boolean hasLoop=request.sourceCode().contains("while")||request.sourceCode().contains("for");
    int passed=hasLoop?4:2; double updated=mastery.record(request.learnerId(),"binary-search",passed/6.0);
    return Map.of("status","COMPLETED","passed",passed,"total",6,"mastery",updated,"aiFeedback",hasLoop?"Good decomposition. Recheck duplicate boundaries.":"Avoid a linear scan; preserve O(log n).","understandingCheck","Why is two-pass binary search still O(log n)?");
  }
  @PostMapping("/hints") Map<String,String> hint(){return Map.of("hint","When nums[mid] equals target, save mid and continue toward the boundary.");}
  private Map<String,Object> topic(String id,String name,double score,boolean unlocked,List<String> prerequisites){
    Map<String,Object> result=new LinkedHashMap<>();result.put("id",id);result.put("name",name);result.put("mastery",score);result.put("unlocked",unlocked);result.put("prerequisites",prerequisites);return result;
  }
  public record Submission(@NotBlank String learnerId,@NotBlank String questionId,@NotBlank String language,@NotBlank String sourceCode){}
}
