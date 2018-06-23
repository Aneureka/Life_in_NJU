package leftovers.controller;

import leftovers.model.Algorithm;
import leftovers.model.SimpleAlgorithm;
import leftovers.service.AlgorithmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by Hiki on 2017/6/9.
 */
@Controller
@RequestMapping(value = "/api/algorithm", produces = "application/json;charset=UTF-8")
public class AlgorithmController {

    @Autowired
    AlgorithmService algorithmService;

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public long createAlgorithm(@RequestBody Algorithm algorithm){
        return algorithmService.createAlgorithm(algorithm);
    }

    @RequestMapping(value = "/createDefault", method = RequestMethod.POST)
    @ResponseBody
    public long createDefaultAlgorithm(
            @RequestBody SimpleAlgorithm simpleAlgorithm){
        return algorithmService.createDefaultAlgorithm(simpleAlgorithm);
    }


    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public long updateAlgorithm(@RequestBody Algorithm algorithm){
        return algorithmService.updateAlgorithm(algorithm);
    }

    @RequestMapping(value = "/remove", method = RequestMethod.GET)
    @ResponseBody
    public long removeAlgorithm(@RequestParam String algoId){
        return algorithmService.removeAlgorithm(algoId);
    }

    @RequestMapping(value = "/findAlgorithmById", method = RequestMethod.GET)
    public @ResponseBody
    Algorithm findAlgorithmById(@RequestParam String algoId){
        return algorithmService.findAlgorithmById(algoId);
    }

    @RequestMapping(value = "/findAlgorithmByUsername", method = RequestMethod.GET)
    public @ResponseBody
    List<Algorithm> findAlgorithmByUsername(@RequestParam String username){
        return algorithmService.findAlgorithmsByUsername(username);
    }

}
