package jp.smartcompany.dynamicdatasourceactiviti.config;

import com.baomidou.dynamic.datasource.DynamicRoutingDataSource;
import lombok.RequiredArgsConstructor;
import org.activiti.engine.impl.cfg.multitenant.TenantInfoHolder;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Deque;

@Component
@RequiredArgsConstructor
public class WorkflowTenantInfoHolder implements TenantInfoHolder {

    private final DataSource dataSource;

    @Override
    public Collection<String> getAllTenants() {
        var ds = (DynamicRoutingDataSource) dataSource;
        return ds.getDataSources().keySet();
    }

    /**
     * 为什么要用链表存储(准确的是栈)
     * <pre>
     * 为了支持嵌套切换，如ABC三个service都是不同的数据源
     * 其中A的某个业务要调B的方法，B的方法需要调用C的方法。一级一级调用切换，形成了链。
     * 传统的只设置当前线程的方式不能满足此业务需求，必须使用栈，后进先出。
     * </pre>
     */
    private static final ThreadLocal<Deque<String>> LOOKUP_KEY_HOLDER = new NamedThreadLocal<Deque<String>>("dynamic-datasource") {
        @Override
        protected Deque<String> initialValue() {
            return new ArrayDeque<>();
        }
    };


    @Override
    public void setCurrentTenantId(String tenantId) {
        String dataSourceStr = StringUtils.isEmpty(tenantId) ? "" : tenantId;
        LOOKUP_KEY_HOLDER.get().push(dataSourceStr);
    }

    @Override
    public String getCurrentTenantId() {
        return LOOKUP_KEY_HOLDER.get().peek();
    }

    @Override
    public void clearCurrentTenantId() {
        LOOKUP_KEY_HOLDER.remove();
    }

}
