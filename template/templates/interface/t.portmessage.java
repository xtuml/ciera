    public void ${self.msg_name}(${parameter_list}) throws XtumlException \
.if ( inbound )
${body}
.else
{
        if ( satisfied() ) send( new Message( "${self.msg_name}"${invocation_parameter_list} ) );
        else ${body}\
    }
.end if
