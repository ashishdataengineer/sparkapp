package sparkProject;

import org.springframework.context.ApplicationContext;

public abstract class AbstractProcessingLoad {

	public static Processor getProcessor(String beanId) {

		ApplicationContext ctx = ContextLoader.loadContext();
		return (Processor) ctx.getBean(beanId);

	}

	public static void getProcessor() {

		ContextLoader.loadContext();

	}

}
