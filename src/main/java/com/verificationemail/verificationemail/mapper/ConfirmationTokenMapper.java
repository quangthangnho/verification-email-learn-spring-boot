package com.verificationemail.verificationemail.mapper;

import com.verificationemail.verificationemail.dto.ConfirmationTokenDto;
import com.verificationemail.verificationemail.model.ConfirmationToken;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ConfirmationTokenMapper {

    ConfirmationTokenDto entityToDto(ConfirmationToken confirmationToken);

    ConfirmationToken  dtoToEntity(ConfirmationTokenDto confirmationTokenDto);
}
