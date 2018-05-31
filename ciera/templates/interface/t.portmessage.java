    public void ${self.msg_name}(${parameter_list}) throws XtumlException \
.if ( inbound )
${body}
.else
{
        send( null, new Message( "${self.msg_name}"${invocation_parameter_list} ) );
    }
.end if
