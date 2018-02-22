
package controllers;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import domain.Comment;
import domain.MatchForecast;
import services.CommentService;
import services.DayService;

@Controller
@RequestMapping("/comment")
public class CommentController extends AbstractController {

	@Autowired
	private CommentService	commentService;
	@Autowired
	private DayService		dayService;


	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		Collection<Comment> comment = new ArrayList<Comment>();
		try {
			result = new ModelAndView("comment/list");
			result.addObject("requestURI", "comment/list.do");
			for (MatchForecast a : dayService.ultimaJornada().getMatchesForecast()) {
				for (Comment b : a.getComments()) {
					comment.add(b);
				}
			}
			result.addObject("comment", comment);
			result.addObject("num", dayService.ultimaJornada().getNum());
		} catch (final Throwable e) {
			result = new ModelAndView("redirect:/welcome/index.do");
		}

		return result;
	}

}
