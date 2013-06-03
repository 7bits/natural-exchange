package it.sevenbits.controller;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

import it.sevenbits.dao.AdvertisementDao;
import it.sevenbits.entity.Advertisement;
import it.sevenbits.entity.hibernate.AdvertisementEntity;
import it.sevenbits.util.SortOrder;

import it.sevenbits.util.form.AdvertisementPlacingForm;
import it.sevenbits.util.form.validator.AdvertisementPlacingValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.support.PagedListHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Annoted spring controller class
 */
@Controller
@RequestMapping(value = "advertisement")
public class AdvertisementController {

	@Resource(name = "advertisementDao")
	private AdvertisementDao advertisementDao;

	/**
	 * Gives information about all advertisements for display
	 * 
	 * @return jsp-page with advertisements information
	 */

	@RequestMapping(value = "/list.html", method = RequestMethod.GET)
	public ModelAndView list(
			@RequestParam(value = "sortedBy", required = false) String sortByNameParam,
			@RequestParam(value = "sortOrder", required = false) String sortOrderParam) {
		ModelAndView modelAndView = new ModelAndView("advertisement/list");
		SortOrder sortOrder = SortOrder
				.getViceVersa((sortOrderParam == null) ? null : SortOrder
						.valueOf(sortOrderParam));
		// Will be ASCENDING order in any wrong cases.
		List<Advertisement> advertisements = this.advertisementDao.findAll(
				sortOrder, sortByNameParam);

		//modelAndView.addObject("advertisements", advertisements);
		PagedListHolder<Advertisement> pageList = new PagedListHolder<Advertisement>(
				advertisements);
		pageList.setPageSize(2);
		pageList.setPage(0);
 		modelAndView.addObject("advertisements", pageList.getPageList());

		if (sortByNameParam != null) {
			if (sortByNameParam.equals("title")) {
				modelAndView.addObject("sortByDateOrderNew",
						SortOrder.NONE.toString());
				modelAndView.addObject("sortByTitleOrderNew",
						sortOrder.toString());
			} else {
				modelAndView.addObject("sortByDateOrderNew",
						sortOrder.toString());
				modelAndView.addObject("sortByTitleOrderNew",
						SortOrder.NONE.toString());
			}
		} else {
			modelAndView.addObject("sortByDateOrderNew",
					SortOrder.NONE.toString());
			modelAndView.addObject("sortByTitleOrderNew",
					SortOrder.NONE.toString());
		}
		List<String> categories = new ArrayList<String>();
		categories.add("Игрушки");
		categories.add("Одежда");
		categories.add("Мебель");
		modelAndView.addObject("categories", categories);

		return modelAndView;
	}

	/**
	 * Gives information about one advertisement by id for display
	 * 
	 * @return jsp-page with advertisement information
	 */
	@RequestMapping(value = "/view.html", method = RequestMethod.GET)
	public ModelAndView view(@RequestParam(required = true) String id) {

		// Создаем вьюшку по list.jsp, которая выведется этим контроллером на
		// экран
		ModelAndView modelAndView = new ModelAndView("advertisement/list");
		List<Advertisement> advertisements = this.advertisementDao.findAll();
		modelAndView.addObject("advertisements", advertisements);
		List<String> categories = new ArrayList<String>();
		categories.add("Игрушки");
		categories.add("Одежда");
		categories.add("Мебель");
		modelAndView.addObject("categories", categories);

		return modelAndView;
	}

    @Autowired
    private AdvertisementPlacingValidator advertisementPlacingValidator;

    @RequestMapping(value = "/placing.html", method = RequestMethod.GET)
    public ModelAndView placing() {
        ModelAndView modelAndView = new ModelAndView("advertisement/placing");
        AdvertisementPlacingForm advertisementPlacingForm = new AdvertisementPlacingForm();
        modelAndView.addObject("advertisementPlacingForm", advertisementPlacingForm);
        return modelAndView;
    }

    @RequestMapping(value = "/placing.html",method = RequestMethod.POST)
    public ModelAndView processPlacing(AdvertisementPlacingForm advertisementPlacingForm, BindingResult result) {
        advertisementPlacingValidator.validate(advertisementPlacingForm, result);
        if (result.hasErrors()) {
            return new ModelAndView("advertisement/placing");
        }
        AdvertisementEntity tmp = new AdvertisementEntity();
        tmp.setText(advertisementPlacingForm.getText());
        tmp.setPhotoFile(advertisementPlacingForm.getPhotoFile());
        tmp.setTitle(advertisementPlacingForm.getTitle());
        this.advertisementDao.create(tmp);
        return new ModelAndView("advertisement/placingRequest");
    }
}
