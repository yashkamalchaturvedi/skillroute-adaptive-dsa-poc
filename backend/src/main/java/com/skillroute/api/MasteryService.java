package com.skillroute.api;
import org.springframework.stereotype.Service;
import java.util.concurrent.ConcurrentHashMap;
@Service public class MasteryService {
  private final ConcurrentHashMap<String,Double> scores=new ConcurrentHashMap<>();
  double score(String learner,String topic){ return scores.getOrDefault(learner+":"+topic,68.0); }
  double record(String learner,String topic,double performance){
    return scores.compute(learner+":"+topic,(key,old)->{
      double prior=old==null?68:old, evidence=performance*100;
      return Math.round((prior*.75+evidence*.25)*10.0)/10.0;
    });
  }
}
