/*
 * WelcomeController.java
 *
 * Copyright (C) 2017 Universidad de Sevilla
 *
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import domain.Actor;
import security.LoginService;
import services.AnalisisService;
import services.CommentProcessService;
import services.CommentService;
import services.DayService;
import services.GenerateArchiveArff;
import services.MatchFinalService;
import services.MatchForecastService;

@Controller
@RequestMapping("/welcome")
public class WelcomeController extends AbstractController {

	@Autowired
	private LoginService			loginService;
	@Autowired
	private MatchForecastService	matchForecastService;
	@Autowired
	private CommentService			commentService;
	@Autowired
	private DayService				dayService;
	@Autowired
	private MatchFinalService		matchFinalService;
	@Autowired
	private CommentProcessService	commentProcessService;
	@Autowired
	private GenerateArchiveArff		generateArchiveArff;
	@Autowired
	private AnalisisService			analisisService;


	// Constructors -----------------------------------------------------------

	public WelcomeController() {
		super();
	}

	// Index ------------------------------------------------------------------

	@RequestMapping(value = "/index")
	public ModelAndView index(@RequestParam(required = false, defaultValue = "John Doe") final String name) {
		ModelAndView result;
		SimpleDateFormat formatter;
		String moment;

		formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		moment = formatter.format(new Date());

		result = new ModelAndView("welcome/index");
		if (LoginService.isAnyAuthenticated() == true) {
			final Actor a = this.loginService.findActorByUsername(LoginService.getPrincipal().getUsername());
			result.addObject("name", a.getName());
		} else
			result.addObject("name", name);

		result.addObject("moment", moment);
		result.addObject("matchForecast", dayService.ultimaJornada().getMatchesForecast());
		result.addObject("matchFinal", dayService.ultimaJornada().getMatchesFinal());
		result.addObject("num", dayService.ultimaJornada().getNum());

		return result;
	}

	@RequestMapping(value = "/cookies")
	public ModelAndView cookies() {
		ModelAndView result;

		result = new ModelAndView("legislation/cookies");

		return result;
	}

	@RequestMapping(value = "/lopd")
	public ModelAndView lopd() {
		ModelAndView result;

		result = new ModelAndView("legislation/lopd");

		return result;
	}

	@RequestMapping(value = "/lssi")
	public ModelAndView lssi() {
		ModelAndView result;

		result = new ModelAndView("legislation/lssi");

		return result;
	}

	@RequestMapping(value = "/cargar")
	public ModelAndView saveManagerCreate() {
		ModelAndView result = new ModelAndView("welcome/index");

		try {
			//matchForecastService.guardarPartidos();
			//commentService.guardarComentarios();
			commentProcessService.guardarComentariosProcesados();
			//matchFinalService.guardarResultadoFinal();
			result = new ModelAndView("redirect:/");
		} catch (final Throwable th) {
			th.printStackTrace();
		}
		return result;
	}

	@RequestMapping(value = "/procesar")
	public ModelAndView procesarArchivoWeka() {
		ModelAndView result = new ModelAndView("welcome/index");

		try {
			//matchForecastService.guardarPartidos();
			//commentService.guardarComentarios();
			generateArchiveArff.generarArchivoWeka();

			//matchFinalService.guardarResultadoFinal();
			result = new ModelAndView("redirect:/");
		} catch (final Throwable th) {
			th.printStackTrace();
		}
		return result;
	}

}
