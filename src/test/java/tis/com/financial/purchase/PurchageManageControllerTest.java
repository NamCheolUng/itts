package tis.com.financial.purchase;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.handler;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import javax.inject.Inject;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import tis.com.financial.purchase.web.PurchaseManageController;
import tis.com.test.common.TisApplicationContextTestBase;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class PurchageManageControllerTest extends TisApplicationContextTestBase {
	@Inject
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void purchaseManageListTest() throws Exception {
		mockMvc.perform(
				post("/com/financial/purchase/purchaseManageList.do")
					.param("searchDeptCode", "DEPT03"))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(PurchaseManageController.class))
				.andExpect(model().attributeExists("resultList"))
				.andExpect(model().attributeExists("searchVO"))
				.andExpect(model().attributeExists("deptList"))
				.andExpect(model().attributeExists("adbkList"))
				.andExpect(view().name("tis/com/financial/purchase/purchaseManageList"));
		
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/com/financial/accountingProfitLossList.do"));
//		System.out.println("view name : " + resultActions.andReturn().getModelAndView().getViewName());
	}

}
