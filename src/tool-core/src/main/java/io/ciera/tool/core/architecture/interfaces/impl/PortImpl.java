package io.ciera.tool.core.architecture.interfaces.impl;


import io.ciera.runtime.instanceloading.AttributeChangedDelta;
import io.ciera.runtime.instanceloading.InstanceCreatedDelta;
import io.ciera.runtime.summit.application.IRunContext;
import io.ciera.runtime.summit.classes.IInstanceIdentifier;
import io.ciera.runtime.summit.classes.InstanceIdentifier;
import io.ciera.runtime.summit.classes.ModelInstance;
import io.ciera.runtime.summit.exceptions.EmptyInstanceException;
import io.ciera.runtime.summit.exceptions.InstancePopulationException;
import io.ciera.runtime.summit.exceptions.XtumlException;
import io.ciera.runtime.summit.types.IWhere;
import io.ciera.runtime.summit.types.IXtumlType;
import io.ciera.runtime.summit.types.StringUtil;
import io.ciera.runtime.summit.types.UniqueId;
import io.ciera.tool.Core;
import io.ciera.tool.core.architecture.component.ComponentDefinition;
import io.ciera.tool.core.architecture.component.impl.ComponentDefinitionImpl;
import io.ciera.tool.core.architecture.file.File;
import io.ciera.tool.core.architecture.file.impl.FileImpl;
import io.ciera.tool.core.architecture.interfaces.Iface;
import io.ciera.tool.core.architecture.interfaces.Message;
import io.ciera.tool.core.architecture.interfaces.MessageSet;
import io.ciera.tool.core.architecture.interfaces.Port;
import io.ciera.tool.core.architecture.interfaces.PortMessage;
import io.ciera.tool.core.architecture.interfaces.PortMessageSet;
import io.ciera.tool.core.architecture.interfaces.impl.IfaceImpl;
import io.ciera.tool.core.architecture.interfaces.impl.PortImpl;
import io.ciera.tool.core.architecture.interfaces.impl.PortMessageSetImpl;
import io.ciera.tool.core.ooaofmarking.Mark;
import io.ciera.tool.core.ooaofooa.component.C_C;
import io.ciera.tool.core.ooaofooa.component.C_PO;
import io.ciera.tool.core.ooaofooa.component.impl.C_POImpl;
import io.ciera.tool.core.ooaofooa.packageableelement.PackageableElement;

import java.util.Iterator;

import types.ImportType;


public class PortImpl extends ModelInstance<Port,Core> implements Port {

    public static final String KEY_LETTERS = "Port";
    public static final Port EMPTY_PORT = new EmptyPort();

    private Core context;

    // constructors
    private PortImpl( Core context ) {
        this.context = context;
        ref_name = "";
        ref_package = "";
        m_port_name = "";
        m_provider = false;
        ref_comp_name = "";
        ref_comp_package = "";
        ref_iface_name = "";
        ref_iface_package = "";
        ref_provider_port_name = "";
        ref_provider_port_package = "";
        m_base_class = "";
        ref_port_Id = UniqueId.random();
        R401_is_a_File_inst = FileImpl.EMPTY_FILE;
        R4160_satisfies_provided_Port_inst = PortImpl.EMPTY_PORT;
        R4160_satisfies_required_Port_inst = PortImpl.EMPTY_PORT;
        R417_passes_messages_for_ComponentDefinition_inst = ComponentDefinitionImpl.EMPTY_COMPONENTDEFINITION;
        R418_implements_Iface_inst = IfaceImpl.EMPTY_IFACE;
        R420_provides_implementation_for_PortMessage_set = new PortMessageSetImpl();
        R422_is_transformed_from_C_PO_inst = C_POImpl.EMPTY_C_PO;
    }

    private PortImpl( Core context, UniqueId instanceId, String ref_name, String ref_package, String m_port_name, boolean m_provider, String ref_comp_name, String ref_comp_package, String ref_iface_name, String ref_iface_package, String ref_provider_port_name, String ref_provider_port_package, String m_base_class, UniqueId ref_port_Id ) {
        super(instanceId);
        this.context = context;
        this.ref_name = ref_name;
        this.ref_package = ref_package;
        this.m_port_name = m_port_name;
        this.m_provider = m_provider;
        this.ref_comp_name = ref_comp_name;
        this.ref_comp_package = ref_comp_package;
        this.ref_iface_name = ref_iface_name;
        this.ref_iface_package = ref_iface_package;
        this.ref_provider_port_name = ref_provider_port_name;
        this.ref_provider_port_package = ref_provider_port_package;
        this.m_base_class = m_base_class;
        this.ref_port_Id = ref_port_Id;
        R401_is_a_File_inst = FileImpl.EMPTY_FILE;
        R4160_satisfies_provided_Port_inst = PortImpl.EMPTY_PORT;
        R4160_satisfies_required_Port_inst = PortImpl.EMPTY_PORT;
        R417_passes_messages_for_ComponentDefinition_inst = ComponentDefinitionImpl.EMPTY_COMPONENTDEFINITION;
        R418_implements_Iface_inst = IfaceImpl.EMPTY_IFACE;
        R420_provides_implementation_for_PortMessage_set = new PortMessageSetImpl();
        R422_is_transformed_from_C_PO_inst = C_POImpl.EMPTY_C_PO;
    }

    public static Port create( Core context ) throws XtumlException {
        Port newPort = new PortImpl( context );
        if ( context.addInstance( newPort ) ) {
            newPort.getRunContext().addChange(new InstanceCreatedDelta(newPort, KEY_LETTERS));
            return newPort;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }

    public static Port create( Core context, UniqueId instanceId, String ref_name, String ref_package, String m_port_name, boolean m_provider, String ref_comp_name, String ref_comp_package, String ref_iface_name, String ref_iface_package, String ref_provider_port_name, String ref_provider_port_package, String m_base_class, UniqueId ref_port_Id ) throws XtumlException {
        Port newPort = new PortImpl( context, instanceId, ref_name, ref_package, m_port_name, m_provider, ref_comp_name, ref_comp_package, ref_iface_name, ref_iface_package, ref_provider_port_name, ref_provider_port_package, m_base_class, ref_port_Id );
        if ( context.addInstance( newPort ) ) {
            return newPort;
        }
        else throw new InstancePopulationException( "Instance already exists within this population." );
    }



    // attributes
    private String ref_name;
    @Override
    public String getName() throws XtumlException {
        checkLiving();
        return ref_name;
    }
    @Override
    public void setName(String ref_name) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_name, this.ref_name)) {
            final String oldValue = this.ref_name;
            this.ref_name = ref_name;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_name", oldValue, this.ref_name));
            if ( !R4160_satisfies_required_Port().isEmpty() ) R4160_satisfies_required_Port().setProvider_port_name( ref_name );
            if ( !R420_provides_implementation_for_PortMessage().isEmpty() ) R420_provides_implementation_for_PortMessage().setPort_name( ref_name );
        }
    }
    private String ref_package;
    @Override
    public String getPackage() throws XtumlException {
        checkLiving();
        return ref_package;
    }
    @Override
    public void setPackage(String ref_package) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_package, this.ref_package)) {
            final String oldValue = this.ref_package;
            this.ref_package = ref_package;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_package", oldValue, this.ref_package));
            if ( !R420_provides_implementation_for_PortMessage().isEmpty() ) R420_provides_implementation_for_PortMessage().setPort_package( ref_package );
            if ( !R4160_satisfies_required_Port().isEmpty() ) R4160_satisfies_required_Port().setProvider_port_package( ref_package );
        }
    }
    private String m_port_name;
    @Override
    public void setPort_name(String m_port_name) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(m_port_name, this.m_port_name)) {
            final String oldValue = this.m_port_name;
            this.m_port_name = m_port_name;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_port_name", oldValue, this.m_port_name));
        }
    }
    @Override
    public String getPort_name() throws XtumlException {
        checkLiving();
        return m_port_name;
    }
    private boolean m_provider;
    @Override
    public void setProvider(boolean m_provider) throws XtumlException {
        checkLiving();
        if (m_provider != this.m_provider) {
            final boolean oldValue = this.m_provider;
            this.m_provider = m_provider;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_provider", oldValue, this.m_provider));
        }
    }
    @Override
    public boolean getProvider() throws XtumlException {
        checkLiving();
        return m_provider;
    }
    private String ref_comp_name;
    @Override
    public String getComp_name() throws XtumlException {
        checkLiving();
        return ref_comp_name;
    }
    @Override
    public void setComp_name(String ref_comp_name) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_comp_name, this.ref_comp_name)) {
            final String oldValue = this.ref_comp_name;
            this.ref_comp_name = ref_comp_name;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_comp_name", oldValue, this.ref_comp_name));
        }
    }
    private String ref_comp_package;
    @Override
    public String getComp_package() throws XtumlException {
        checkLiving();
        return ref_comp_package;
    }
    @Override
    public void setComp_package(String ref_comp_package) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_comp_package, this.ref_comp_package)) {
            final String oldValue = this.ref_comp_package;
            this.ref_comp_package = ref_comp_package;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_comp_package", oldValue, this.ref_comp_package));
        }
    }
    private String ref_iface_name;
    @Override
    public String getIface_name() throws XtumlException {
        checkLiving();
        return ref_iface_name;
    }
    @Override
    public void setIface_name(String ref_iface_name) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_iface_name, this.ref_iface_name)) {
            final String oldValue = this.ref_iface_name;
            this.ref_iface_name = ref_iface_name;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_iface_name", oldValue, this.ref_iface_name));
        }
    }
    private String ref_iface_package;
    @Override
    public String getIface_package() throws XtumlException {
        checkLiving();
        return ref_iface_package;
    }
    @Override
    public void setIface_package(String ref_iface_package) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_iface_package, this.ref_iface_package)) {
            final String oldValue = this.ref_iface_package;
            this.ref_iface_package = ref_iface_package;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_iface_package", oldValue, this.ref_iface_package));
        }
    }
    private String ref_provider_port_name;
    @Override
    public String getProvider_port_name() throws XtumlException {
        checkLiving();
        return ref_provider_port_name;
    }
    @Override
    public void setProvider_port_name(String ref_provider_port_name) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_provider_port_name, this.ref_provider_port_name)) {
            final String oldValue = this.ref_provider_port_name;
            this.ref_provider_port_name = ref_provider_port_name;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_provider_port_name", oldValue, this.ref_provider_port_name));
        }
    }
    private String ref_provider_port_package;
    @Override
    public String getProvider_port_package() throws XtumlException {
        checkLiving();
        return ref_provider_port_package;
    }
    @Override
    public void setProvider_port_package(String ref_provider_port_package) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(ref_provider_port_package, this.ref_provider_port_package)) {
            final String oldValue = this.ref_provider_port_package;
            this.ref_provider_port_package = ref_provider_port_package;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_provider_port_package", oldValue, this.ref_provider_port_package));
        }
    }
    private String m_base_class;
    @Override
    public String getBase_class() throws XtumlException {
        checkLiving();
        return m_base_class;
    }
    @Override
    public void setBase_class(String m_base_class) throws XtumlException {
        checkLiving();
        if (StringUtil.inequality(m_base_class, this.m_base_class)) {
            final String oldValue = this.m_base_class;
            this.m_base_class = m_base_class;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "m_base_class", oldValue, this.m_base_class));
        }
    }
    private UniqueId ref_port_Id;
    @Override
    public UniqueId getPort_Id() throws XtumlException {
        checkLiving();
        return ref_port_Id;
    }
    @Override
    public void setPort_Id(UniqueId ref_port_Id) throws XtumlException {
        checkLiving();
        if (ref_port_Id.inequality( this.ref_port_Id)) {
            final UniqueId oldValue = this.ref_port_Id;
            this.ref_port_Id = ref_port_Id;
            getRunContext().addChange(new AttributeChangedDelta(this, KEY_LETTERS, "ref_port_Id", oldValue, this.ref_port_Id));
        }
    }


    // instance identifiers
    @Override
    public IInstanceIdentifier getId1() {
        try {
            return new InstanceIdentifier(getName(), getPackage());
        }
        catch ( XtumlException e ) {
            getRunContext().getLog().error(e);
            System.exit(1);
            return null;
        }
    }

    // operations
    @Override
    public void render() throws XtumlException {
        File file = self().R401_is_a_File();
        String imports = file.getFormattedImports( ImportType.IMPL );
        MessageSet inbound_msgs = ((MessageSet)self().R418_implements_Iface().R419_defines_communication_through_Message().where(selected -> !((Message)selected).getTo_provider() && !self().getProvider() || ((Message)selected).getTo_provider() && self().getProvider()));
        PortMessageSet inbound_port_msgs = ((PortMessageSet)inbound_msgs.R420_is_implemented_within_PortMessage().where(selected -> StringUtil.equality(((PortMessage)selected).getPort_name(), self().getName())));
        PortMessage port_msg;
        for ( Iterator<PortMessage> _port_msg_iter = inbound_port_msgs.elements().iterator(); _port_msg_iter.hasNext(); ) {
            port_msg = _port_msg_iter.next();
            port_msg.render();
        }
        String inbound_message_block = context().T().body();
        context().T().clear();
        MessageSet outbound_msgs = ((MessageSet)self().R418_implements_Iface().R419_defines_communication_through_Message().where(selected -> ((Message)selected).getTo_provider() && !self().getProvider() || !((Message)selected).getTo_provider() && self().getProvider()));
        PortMessageSet outbound_port_msgs = ((PortMessageSet)outbound_msgs.R420_is_implemented_within_PortMessage().where(selected -> StringUtil.equality(((PortMessage)selected).getPort_name(), self().getName())));
        for ( Iterator<PortMessage> _port_msg_iter = outbound_port_msgs.elements().iterator(); _port_msg_iter.hasNext(); ) {
            port_msg = _port_msg_iter.next();
            port_msg.render();
        }
        String outbound_message_block = context().T().body();
        context().T().clear();
        PortMessage msg;
        for ( Iterator<PortMessage> _msg_iter = inbound_port_msgs.elements().iterator(); _msg_iter.hasNext(); ) {
            msg = _msg_iter.next();
            msg.render_deliver();
        }
        String message_switch_block = context().T().body();
        context().T().clear();
        String extra_parameters = "";
        if ( StringUtil.equality("HttpPort", self().getBase_class()) ) {
            C_PO c_po = self().R422_is_transformed_from_C_PO();
            C_C c_c = c_po.R4010_appears_in_C_C();
            PackageableElement pe = c_c.R8001_is_a_PackageableElement();
            Mark http_endpoint_mark = context().Mark_instances().anyWhere(selected -> ( StringUtil.equality(((Mark)selected).getMarkable_name(), "Port") && StringUtil.equality(((Mark)selected).getPath(), ( ( ( context().containerMarkingPath( pe ) + "::" ) + c_c.getName() ) + "::" ) + c_po.getName()) ) && StringUtil.equality(((Mark)selected).getFeature_name(), "HttpEndpoint"));
            String endpoint = http_endpoint_mark.getValue();
            context().T().include( "interface/t.port.http.java", endpoint );
            extra_parameters = context().T().body();
            context().T().clear();
        }
        context().T().include( "interface/t.port.java", extra_parameters, imports, inbound_message_block, message_switch_block, outbound_message_block, self() );
        context().T().emit( ( ( file.getPath() + "/" ) + self().getName() ) + file.getExtension() );
        context().T().clear();
    }

    @Override
    public void render_acccessor() throws XtumlException {
        context().T().include( "interface/t.portaccessor.java", self() );
    }



    // static operations


    // events


    // selections
    private File R401_is_a_File_inst;
    @Override
    public void setR401_is_a_File( File inst ) {
        R401_is_a_File_inst = inst;
    }
    @Override
    public File R401_is_a_File() throws XtumlException {
        return R401_is_a_File_inst;
    }
    private Port R4160_satisfies_provided_Port_inst;
    @Override
    public void setR4160_satisfies_provided_Port( Port inst ) {
        R4160_satisfies_provided_Port_inst = inst;
    }
    @Override
    public Port R4160_satisfies_provided_Port() throws XtumlException {
        return R4160_satisfies_provided_Port_inst;
    }
    private Port R4160_satisfies_required_Port_inst;
    @Override
    public void setR4160_satisfies_required_Port( Port inst ) {
        R4160_satisfies_required_Port_inst = inst;
    }
    @Override
    public Port R4160_satisfies_required_Port() throws XtumlException {
        return R4160_satisfies_required_Port_inst;
    }
    private ComponentDefinition R417_passes_messages_for_ComponentDefinition_inst;
    @Override
    public void setR417_passes_messages_for_ComponentDefinition( ComponentDefinition inst ) {
        R417_passes_messages_for_ComponentDefinition_inst = inst;
    }
    @Override
    public ComponentDefinition R417_passes_messages_for_ComponentDefinition() throws XtumlException {
        return R417_passes_messages_for_ComponentDefinition_inst;
    }
    private Iface R418_implements_Iface_inst;
    @Override
    public void setR418_implements_Iface( Iface inst ) {
        R418_implements_Iface_inst = inst;
    }
    @Override
    public Iface R418_implements_Iface() throws XtumlException {
        return R418_implements_Iface_inst;
    }
    private PortMessageSet R420_provides_implementation_for_PortMessage_set;
    @Override
    public void addR420_provides_implementation_for_PortMessage( PortMessage inst ) {
        R420_provides_implementation_for_PortMessage_set.add(inst);
    }
    @Override
    public void removeR420_provides_implementation_for_PortMessage( PortMessage inst ) {
        R420_provides_implementation_for_PortMessage_set.remove(inst);
    }
    @Override
    public PortMessageSet R420_provides_implementation_for_PortMessage() throws XtumlException {
        return R420_provides_implementation_for_PortMessage_set;
    }
    private C_PO R422_is_transformed_from_C_PO_inst;
    @Override
    public void setR422_is_transformed_from_C_PO( C_PO inst ) {
        R422_is_transformed_from_C_PO_inst = inst;
    }
    @Override
    public C_PO R422_is_transformed_from_C_PO() throws XtumlException {
        return R422_is_transformed_from_C_PO_inst;
    }


    @Override
    public IRunContext getRunContext() {
        return context().getRunContext();
    }

    @Override
    public Core context() {
        return context;
    }

    @Override
    public String getKeyLetters() {
        return KEY_LETTERS;
    }

    @Override
    public Port self() {
        return this;
    }

    @Override
    public Port oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        if (condition.evaluate(this)) return this;
        else return EMPTY_PORT;
    }

}

class EmptyPort extends ModelInstance<Port,Core> implements Port {

    // attributes
    public String getName() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setName( String ref_name ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getPackage() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setPackage( String ref_package ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public void setPort_name( String m_port_name ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getPort_name() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setProvider( boolean m_provider ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public boolean getProvider() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public String getComp_name() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setComp_name( String ref_comp_name ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getComp_package() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setComp_package( String ref_comp_package ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getIface_name() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setIface_name( String ref_iface_name ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getIface_package() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setIface_package( String ref_iface_package ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getProvider_port_name() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setProvider_port_name( String ref_provider_port_name ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getProvider_port_package() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setProvider_port_package( String ref_provider_port_package ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public String getBase_class() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setBase_class( String m_base_class ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }
    public UniqueId getPort_Id() throws XtumlException {
        throw new EmptyInstanceException( "Cannot get attribute of empty instance." );
    }
    public void setPort_Id( UniqueId ref_port_Id ) throws XtumlException {
        throw new EmptyInstanceException( "Cannot set attribute of empty instance." );
    }


    // operations
    public void render() throws XtumlException {
        throw new EmptyInstanceException( "Cannot invoke operation on empty instance." );
    }
    public void render_acccessor() throws XtumlException {
        throw new EmptyInstanceException( "Cannot invoke operation on empty instance." );
    }


    // selections
    @Override
    public File R401_is_a_File() {
        return FileImpl.EMPTY_FILE;
    }
    @Override
    public Port R4160_satisfies_provided_Port() {
        return PortImpl.EMPTY_PORT;
    }
    @Override
    public Port R4160_satisfies_required_Port() {
        return PortImpl.EMPTY_PORT;
    }
    @Override
    public ComponentDefinition R417_passes_messages_for_ComponentDefinition() {
        return ComponentDefinitionImpl.EMPTY_COMPONENTDEFINITION;
    }
    @Override
    public Iface R418_implements_Iface() {
        return IfaceImpl.EMPTY_IFACE;
    }
    @Override
    public PortMessageSet R420_provides_implementation_for_PortMessage() {
        return (new PortMessageSetImpl());
    }
    @Override
    public C_PO R422_is_transformed_from_C_PO() {
        return C_POImpl.EMPTY_C_PO;
    }


    @Override
    public String getKeyLetters() {
        return PortImpl.KEY_LETTERS;
    }

    @Override
    public Port self() {
        return this;
    }

    @Override
    public boolean isEmpty() {
        return true;
    }

    @Override
    public Port oneWhere(IWhere<IXtumlType> condition) throws XtumlException {
        if (null == condition) throw new XtumlException("Null condition passed to selection.");
        return PortImpl.EMPTY_PORT;
    }

}
