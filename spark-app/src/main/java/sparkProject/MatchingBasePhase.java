package sparkProject;

import java.util.List;

public class MatchingBasePhase implements Processor {

	private List<MatchingExecutor> MatchingExecutorList;

	public void setMatchingExecutorList(List<MatchingExecutor> MatchingExecutorList) {

		this.MatchingExecutorList = MatchingExecutorList;
	}

	public List<MatchingExecutor> getMatchingExecutorList() {

		return this.MatchingExecutorList;
	}

	public void matchData() {

		for (MatchingExecutor matchingExecutor : MatchingExecutorList) {

			matchingExecutor.execute();

		}

	}

}
