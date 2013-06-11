package co.mv.stm.service.dom.mysql;

import co.mv.stm.Transition;
import co.mv.stm.impl.database.mysql.MySqlCreateDatabaseTransition;
import co.mv.stm.service.dom.BaseDomTransitionBuilder;
import java.util.UUID;

public class MySqlCreateDatabaseDomTransitionBuilder extends BaseDomTransitionBuilder
{
	@Override public Transition build(UUID transitionId, UUID fromStateId, UUID toStateId)
	{
		return new MySqlCreateDatabaseTransition(transitionId, fromStateId, toStateId);
	}
}