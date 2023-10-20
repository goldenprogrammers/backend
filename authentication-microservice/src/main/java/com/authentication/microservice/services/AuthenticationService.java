package com.authentication.microservice.services;

import com.authentication.microservice.domain.authentication.Authentication;
import com.authentication.microservice.dtos.AuthenticationDTO;
import com.authentication.microservice.exceptions.AuthenticationLoginException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {
    public Authentication login(AuthenticationDTO data) {
        // Fields validations
        if (data.credential() == null || data.credential().isEmpty())
            throw new AuthenticationLoginException.RequiredField("credential");

        if (data.password() == null || data.password().isEmpty())
            throw new AuthenticationLoginException.RequiredField("password");

        // Mock
        if(data.credential().equals("hudson.borges@pantanal.com") && data.password().equals("pantanal.dev"))
            return new Authentication("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOcFRzWG42RUtIdWFMaVdrWWFtNjg3VkswU1c3aHRDNEZBVmhDdXpzLUZvIn0.eyJleHAiOjE2OTc3MTA4NzAsImlhdCI6MTY5NzcwMzY3MCwianRpIjoiMTk0MGEzYzMtMTJiMC00N2ExLTgwZGQtNDdkYWY1M2EyM2JmIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiYzA5ZjNlNjAtODAwNC00YTE4LWI5M2ItOTg3MWJkNDdiNzFiIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicGFudGFuYWwtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6IjhkMjM1NzZmLThjODYtNGFhZS1iZDI5LWJkMmEyZjk1YWZiZCIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wYW50YW5hbC1kZXYiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InBhbnRhbmFsLWNsaWVudCI6eyJyb2xlcyI6WyJ1c2VyIl19LCJhY2NvdW50Ijp7InJvbGVzIjpbIm1hbmFnZS1hY2NvdW50IiwibWFuYWdlLWFjY291bnQtbGlua3MiLCJ2aWV3LXByb2ZpbGUiXX19LCJzY29wZSI6ImVtYWlsIHByb2ZpbGUiLCJzaWQiOiI4ZDIzNTc2Zi04Yzg2LTRhYWUtYmQyOS1iZDJhMmY5NWFmYmQiLCJlbWFpbF92ZXJpZmllZCI6ZmFsc2UsIm5hbWUiOiJIdWRzb24gQm9yZ2VzIiwicHJlZmVycmVkX3VzZXJuYW1lIjoiaHVkc29uLmJvcmdlcyIsImdpdmVuX25hbWUiOiJIdWRzb24iLCJmYW1pbHlfbmFtZSI6IkJvcmdlcyIsImVtYWlsIjoiaHVkc29uLmJvcmdlc0BwYW50YW5hbC5jb20ifQ.B_GEKYO_11U9btWcWN51GNh3vFCaRbuk0Ds_4m34qycjBMr0bBXcQ-_TaXUrTJEOfyeM3rOWb1FZxD82jUH80DIxoB1hpa-9MJzK9FhyX0jPg7ImV1wjtllX3D7d1sxZJGIfQ7AxpvxYY_Fi7rhooZ3xGnJzEpOpejeNmzgO-M6vANqPyvXs6I_CHIlF90IPPGrXha8O4Im3hwX4R-3PoCEzkmR9XeysWcNLJ3W9zlmKgt8wbATXXNuJjWkCDnw3UqChoxTq7VGGrnEM3p7Ck7l8wrMUmyY0FVEtgMJcZkQvfIX5EkRf6IHWLVj0NaV9TJZnyTKOEH_hwgeFxmai3A",
                    7200,
                    "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjMTMwNWU0Ny1jMDlkLTRlNjMtOGFlMi02OTRkYTM5ZjI5OTkifQ.eyJleHAiOjE2OTc3MDU0NzAsImlhdCI6MTY5NzcwMzY3MCwianRpIjoiOWZhYzJmZmYtNGFhZi00MDI5LTg0YzMtY2U1N2FiZDA1MWY4IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvcmVhbG1zL3BhbnRhbmFsLWRldiIsInN1YiI6ImMwOWYzZTYwLTgwMDQtNGExOC1iOTNiLTk4NzFiZDQ3YjcxYiIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJwYW50YW5hbC1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiOGQyMzU3NmYtOGM4Ni00YWFlLWJkMjktYmQyYTJmOTVhZmJkIiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiOGQyMzU3NmYtOGM4Ni00YWFlLWJkMjktYmQyYTJmOTVhZmJkIn0.Cm_DyqltM2pfj0Ztk0cNQv8Ng41nXh9V2B75OZMorxE"
            );
        if(data.credential().equals("awdren.fontao@pantanal.com") && data.password().equals("pantanal.dev"))
            return new Authentication("eyJhbGciOiJSUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJOcFRzWG42RUtIdWFMaVdrWWFtNjg3VkswU1c3aHRDNEZBVmhDdXpzLUZvIn0.eyJleHAiOjE2OTc3MTEwMjcsImlhdCI6MTY5NzcwMzgyNywianRpIjoiNzExZjgyZjUtMGIwMC00NmQ5LTliMWUtMzg3MmQ5N2M2Yjc5IiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJhY2NvdW50Iiwic3ViIjoiMjFlYmUyMjMtYTNhNy00ZTAyLThkZTUtZGFhOGMwZTcwNjkxIiwidHlwIjoiQmVhcmVyIiwiYXpwIjoicGFudGFuYWwtY2xpZW50Iiwic2Vzc2lvbl9zdGF0ZSI6ImMxNWUzMGM2LTUwNDUtNDQxMC1hNjk3LTZlNTZjZDNhM2I4NSIsImFjciI6IjEiLCJhbGxvd2VkLW9yaWdpbnMiOlsiLyoiXSwicmVhbG1fYWNjZXNzIjp7InJvbGVzIjpbIm9mZmxpbmVfYWNjZXNzIiwiZGVmYXVsdC1yb2xlcy1wYW50YW5hbC1kZXYiLCJ1bWFfYXV0aG9yaXphdGlvbiJdfSwicmVzb3VyY2VfYWNjZXNzIjp7InBhbnRhbmFsLWNsaWVudCI6eyJyb2xlcyI6WyJhZG1pbiJdfSwiYWNjb3VudCI6eyJyb2xlcyI6WyJtYW5hZ2UtYWNjb3VudCIsIm1hbmFnZS1hY2NvdW50LWxpbmtzIiwidmlldy1wcm9maWxlIl19fSwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiYzE1ZTMwYzYtNTA0NS00NDEwLWE2OTctNmU1NmNkM2EzYjg1IiwiZW1haWxfdmVyaWZpZWQiOmZhbHNlLCJuYW1lIjoiQXdkcmVuIEZvbnTDo28iLCJwcmVmZXJyZWRfdXNlcm5hbWUiOiJhd2RyZW4uZm9udGFvIiwiZ2l2ZW5fbmFtZSI6IkF3ZHJlbiIsImZhbWlseV9uYW1lIjoiRm9udMOjbyIsImVtYWlsIjoiYXdkcmVuLmZvbnRhb0BwYW50YW5hbC5jb20ifQ.LcG83M_IFQ140pB-tefqgL3wp47g70ZWaR5NlScQEXBxcnMHYeR-xJNAfaqr39z9AHQracFc6H1GHoPZibSwVzsv5bDAh0h78s39DPxPyuu2XqUyQkJKpd-IL5zq6LIazm0bVjFx8BqOZFiQPr9alHO9GLqCCZopioHNExyETJvrj7s051cZp3GsWOpOYJqoBVgdWhXdWFHeWIsCzXlJHWzBZHnSG2Rm9ItjUYt_9f3STjRnY3ym2TLwAKSjXvmkVMdubq_TVT8oBuMF0O-Kaw2HivDQ000Z4kfEVBmXOY8pW8Z37RFz1rUM2BLsLAWovFcLnuoaK0MGQJDgQge8EQ",
                    7200,
                    "eyJhbGciOiJIUzI1NiIsInR5cCIgOiAiSldUIiwia2lkIiA6ICJjMTMwNWU0Ny1jMDlkLTRlNjMtOGFlMi02OTRkYTM5ZjI5OTkifQ.eyJleHAiOjE2OTc3MDU2MjcsImlhdCI6MTY5NzcwMzgyNywianRpIjoiN2MyODVjZDAtYTI2OS00MTQ4LWFhNmQtZDY3ZmMyZTM3NWViIiwiaXNzIjoiaHR0cDovL2xvY2FsaG9zdDo4MDgxL3JlYWxtcy9wYW50YW5hbC1kZXYiLCJhdWQiOiJodHRwOi8vbG9jYWxob3N0OjgwODEvcmVhbG1zL3BhbnRhbmFsLWRldiIsInN1YiI6IjIxZWJlMjIzLWEzYTctNGUwMi04ZGU1LWRhYThjMGU3MDY5MSIsInR5cCI6IlJlZnJlc2giLCJhenAiOiJwYW50YW5hbC1jbGllbnQiLCJzZXNzaW9uX3N0YXRlIjoiYzE1ZTMwYzYtNTA0NS00NDEwLWE2OTctNmU1NmNkM2EzYjg1Iiwic2NvcGUiOiJlbWFpbCBwcm9maWxlIiwic2lkIjoiYzE1ZTMwYzYtNTA0NS00NDEwLWE2OTctNmU1NmNkM2EzYjg1In0.x4LYrSiqh7JRdPd0e9g2qQUVQRYLdFawi0wppuHsDmQ"
            );

        throw new AuthenticationLoginException.CredentialsException();
    }
}
