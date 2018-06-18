package tis.com.financial;

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

import tis.com.financial.profitloss.web.ProfitLossController;
import tis.com.test.common.TisApplicationContextTestBase;


@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
public class ProfitLossControllerTest extends TisApplicationContextTestBase {
	@Inject
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	
//	@InjectMocks
//	private AccountingProfitLossController controller;
//	
//	@Mock
//	private ProfitLossService profitLossService = new ProfitLossServiceImpl();

	@Before
	public void setup() {
		this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
	}

	@Test
	public void profitLossListTest() throws Exception {
		mockMvc.perform(
				post("/com/financial/profitLoss/profitLossList.do")
					.param("searchDeptCode", "DEPT03"))
				.andExpect(status().isOk())
				.andExpect(handler().handlerType(ProfitLossController.class))
				.andExpect(model().attributeExists("resultList"))
				.andExpect(model().attributeExists("paginationInfo"))
				.andExpect(model().attributeExists("searchVO"))
				.andExpect(model().attributeExists("deptList"))
				.andExpect(view().name("tis/com/financial/profitLoss/profitLossList"));
		
//		ResultActions resultActions = mockMvc.perform(MockMvcRequestBuilders.get("/com/financial/accountingProfitLossList.do"));
//		System.out.println("view name : " + resultActions.andReturn().getModelAndView().getViewName());
	}

}
