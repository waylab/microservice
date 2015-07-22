package demo;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DemoRestContoroller {

	@RequestMapping(value="/demo", method=RequestMethod.POST)
	public DemoVo demo(@RequestBody DemoVo demoVo){
		System.out.println(demoVo.toString());
		return new DemoVo("yes!!");
	}
}
