package viresh.test.spring.mvc;

import org.junit.Test;
import org.springframework.binding.mapping.Mapper;
import org.springframework.binding.mapping.MappingResults;
import org.springframework.util.Assert;
import org.springframework.web.servlet.View;
import org.springframework.webflow.config.FlowDefinitionResource;
import org.springframework.webflow.config.FlowDefinitionResourceFactory;
import org.springframework.webflow.core.collection.LocalAttributeMap;
import org.springframework.webflow.core.collection.MutableAttributeMap;
import org.springframework.webflow.engine.EndState;
import org.springframework.webflow.engine.Flow;
import org.springframework.webflow.engine.VariableValueFactory;
import org.springframework.webflow.engine.ViewState;
import org.springframework.webflow.execution.FlowExecution;
import org.springframework.webflow.execution.RequestContext;
import org.springframework.webflow.execution.ViewFactory;
import org.springframework.webflow.mvc.builder.FlowResourceFlowViewResolver;
import org.springframework.webflow.test.MockExternalContext;
import org.springframework.webflow.test.MockFlowBuilderContext;
import org.springframework.webflow.test.execution.AbstractXmlFlowExecutionTests;
import viresh.test.spring.mvc.service.LibraryServiceImpl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class LibraryTest extends AbstractXmlFlowExecutionTests {
    @Override
    protected FlowDefinitionResource getResource(
            FlowDefinitionResourceFactory resourceFactory) {


        FlowDefinitionResource resource = resourceFactory.createResource("file:src/main/webapp/WEB-INF/flows/welcome/welcome.xml");
        Assert.notNull(resource);
        return resource;
    }

    LibraryServiceImpl mockedLibraryService;

    protected void setUp() {
        mockedLibraryService = new LibraryServiceImpl();
    }

//    @Override
//    protected void registerMockFlowBeans(ConfigurableBeanFactory flowBeanFactory) {
//        mockedLibraryService = new LibraryServiceImpl();
//    }

    @Override

    protected void configureFlowBuilderContext(MockFlowBuilderContext builderContext) {

        builderContext.registerBean("libraryService", mockedLibraryService);

    }


    MockExternalContext context = new MockExternalContext();

    @Test
    public void testFlowShouldEnterStartState() {
        this.startFlow(context);
        assertCurrentStateEquals("welcome");

    }

//    @Test
//    public void testIntroductionFlow() {
//        MutableAttributeMap input = new LocalAttributeMap();
//
//        VariableValueFactory valueFactory = mock(VariableValueFactory.class);
//
//        Flow flow = (Flow) getFlowDefinition();
//        FlowExecution flowExecution = getFlowExecutionFactory().createFlowExecution(flow);
//
//        updateFlowExecution(flowExecution);
//
//
//        getFlowExecution().start(input, context);
//
////        startFlow(context);
//        assertCurrentStateEquals("welcome");
//        assertResponseWrittenEquals("welcome", context);
//        context.setEventId("next");
//
//        getFlowExecution().resume(context);
//
//
//        setCurrentState("welcome");
//        getFlowDefinitionRegistry().registerFlowDefinition(createSubflow());
//
//        context.setEventId("next");
//        resumeFlow(context);
//
//        assertCurrentStateEquals("introduction");
//        getFlowExecution().resume(context);
//        assertResponseWrittenEquals("welcome", context);
//
////        context.setEventId("next");
//
////        getFlowExecution().resume(context);
//        assertCurrentStateEquals("menu");
////        getFlowExecution().resume(context);
//        assertResponseWrittenEquals("welcome", context);
//
////        FlowResourceFlowViewResolver resolver = new FlowResourceFlowViewResolver();
////        RequestContext context = mock(RequestContext.class);
////
////        LocalAttributeMap flash = new LocalAttributeMap();
////        when(context.getFlashScope()).thenReturn(flash);
////
////        when(context.getCurrentState()).thenReturn(new MockViewState());
//
//
////        View view = resolver.resolveView("introduction", context);
////        assertModelAttributeNotNull("itroduction",view);
//
////        getFlowExecution().getActiveSession().getAttributes().getOutcome().resume(context);
////        getFlow().getState("introduction");
//
//
////        getFlowScope();
//
//
//    }

    @Test
    public void testIntroductionFlow() {
        MockExternalContext context = new MockExternalContext();

        setCurrentState("welcome");
        getFlowDefinitionRegistry().registerFlowDefinition(createSubflow());

        context.setEventId("next");
        resumeFlow(context);   // this is the one that cause Library Service to be called

        assertCurrentStateEquals("introduction");
        getFlowExecution().resume(context);
        assertResponseWrittenEquals("introduction", context);

//        context.setEventId("next");

//        getFlowExecution().resume(context);
        assertCurrentStateEquals("menu");

//        getFlowExecution().resume(context);
        assertResponseWrittenEquals("introduction", context);



    }


    private Flow createSubflow() {
        Flow mockBookingFlow = new Flow("flows/introduction");
        mockBookingFlow.setInputMapper(new Mapper() {
            public MappingResults map(Object source, Object target) {
// assert that 1L was passed in as input
                return null;
            }
        });
        new EndState(mockBookingFlow, "menu");
        return mockBookingFlow;
    }

    @Test
    public void testMenuFlow() {
        MutableAttributeMap input = new LocalAttributeMap();

        startFlow(context);
        assertCurrentStateEquals("welcome");
        context.setEventId("skip");
        resumeFlow(context);
        assertCurrentStateEquals("menu");


    }

    private class MockViewState extends ViewState {

        public MockViewState() {
            super(new Flow("mockFlow"), "mockView", new ViewFactory() {

                public org.springframework.webflow.execution.View getView(RequestContext context) {
                    throw new UnsupportedOperationException();
                }
            });
        }
    }

}

