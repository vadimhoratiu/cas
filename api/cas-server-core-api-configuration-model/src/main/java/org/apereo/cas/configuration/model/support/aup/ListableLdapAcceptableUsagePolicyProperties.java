package org.apereo.cas.configuration.model.support.aup;

import org.apereo.cas.configuration.support.RequiresModule;

import com.fasterxml.jackson.annotation.JsonFilter;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.ArrayList;

/**
 * This is {@link ListableLdapAcceptableUsagePolicyProperties}.
 *
 * @author Misagh Moayyed
 * @since 6.2.0
 */
@RequiresModule(name = "cas-server-support-aup-ldap")
@Getter
@Setter
@Accessors(chain = true)
@JsonFilter("ListableLdapAcceptableUsagePolicyProperties")
public class ListableLdapAcceptableUsagePolicyProperties extends ArrayList<LdapAcceptableUsagePolicyProperties> {
    private static final long serialVersionUID = -7991011278378393382L;
}
